import React, { useEffect, useState } from 'react'
import { Card, Grid, Box } from "@mui/material";
import DurationSelector from "../components/DurationSelector";
import { FormControl, Select, MenuItem } from "@mui/material";
import axios from "axios";

// const azureSubscriptionTypes = [
//     {
//         id: 1,
//         name: "Azure Subscription 1",
//         value: "azure",
//     },
//     {
//         id: 2,
//         name: "Azure Subscription 2",
//         value: "AzureSubscriptionTwo",
//     },
//     {
//         id: 3,
//         name: "Azure Subscription 3",
//         value: "AzureSubscriptionThree",
//     },
//     {
//         id: 4,
//         name: "Azure Subscription 4",
//         value: "AzureSubscriptionFour",
//     }
// ]

const BillingInformationCard = (props) => {
    const { handleMonthChange, months, setDateRange, setCalling, calling, selectedTenantValue, setSelectedTenantValue, setAzureSubscriptionValue, azureSubscriptions = false, handleSubscriptionChange, handleTenantChange, azureSubscriptionValue = '', azureTenants = false } = props;
    // console.log("props", props)
    const [subscriptionOptions, setSubscriptionOptions] = useState([]);
    const [tenantOptions, setTenantOptions] = useState([]);
    // const [selectedTenantValue, setSelectedTenantValue] = useState('');


    useEffect(() => {


        // fetchServiceOptions();
        fetchTenantOptions()

    }, []);
    useEffect(() => {
        fetchServiceOptions();
    }, [selectedTenantValue])

    const fetchTenantOptions = async () => {
        try {
            const token = localStorage.getItem("token");

            if (token) {
                const config = {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                };

                const response = await axios.get(`http://localhost:8080/azure/distinct-tenant-names`, config);
                // console.log("subscriptions response", response)
                if (response?.data && response?.data?.length > 0) {
                    setTenantOptions(response?.data);
                    const getInitialTenantValue = response?.data?.filter((TenantValue) => TenantValue === "Motivity Labs");
                    // console.log("getInitialSubscription", getInitialTenantValue)
                    if (getInitialTenantValue?.length > 0) {
                        setSelectedTenantValue(getInitialTenantValue[0])
                    }
                    // handleSubscriptionChange(response?.data[0])
                }
                // setSubscriptionOptions(response?.data);
                // setClicked(true);
            } else {
                console.error("Token not found in localStorage or options already fetched");
            }
        } catch (error) {
            console.error("Error fetching service options:", error);
        }
    };
    const fetchServiceOptions = async () => {
        try {
            const token = localStorage.getItem("token");

            if (token) {
                const config = {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                };

                const response = await axios.get(`http://localhost:8080/azure/subscription/tenantName?tenantName=${selectedTenantValue}`, config);
                // console.log("subscriptions response", response)
                if (response?.data && response?.data?.length > 0) {
                    setSubscriptionOptions(response?.data);
                    const getInitialSubscription = response?.data?.filter((subscription) => subscription === "Microsoft Azure Motivity");
                    // console.log("getInitialSubscription", getInitialSubscription)
                    if (getInitialSubscription?.length > 0) {
                        setAzureSubscriptionValue(getInitialSubscription[0])
                    }
                    // handleSubscriptionChange(response?.data[0])
                }
                // setSubscriptionOptions(response?.data);
                // setClicked(true);
            } else {
                console.error("Token not found in localStorage or options already fetched");
            }
        } catch (error) {
            console.error("Error fetching service options:", error);
        }
    };

    const newPropsCss = {
        backgroundColor: "#FFFF",
        // width: "340px",
        width: "90%",
        textAlign: "center",
        ":hover": {
            backgroundColor: "#FFFF",
            color: "black",
        },
        "&.Mui-selected": {
            backgroundColor: "#FFFF !important",
            color: "black",
        },
    };
    return (
        <>
            <Card sx={{ px: 2, py: 4, m: 2 }}>
                <Box
                    component={"div"}
                    sx={{
                        display: "flex",
                        justifyContent: "space-around",
                        alignItems: "center",
                    }}
                >
                    <Grid
                        container
                        // spacing={3}
                        spacing={{ xs: 1, md: 2, lg: 3 }}
                        // justifyContent="center"
                        alignItems="center"
                    >
                        <Grid item xs={12} lg={azureSubscriptions ? 12 : 4} xl={azureSubscriptions ? 12 : 4}>
                            <div className={`h4 fw-bold ${azureSubscriptions ? '' : ''}`}>Billing Information</div>
                        </Grid>
                        {azureTenants ? <Grid item xs={12} sm={6} md={6} lg={3} xl={3} className='d-flex justify-content-center'>
                            <div style={{ width: '100%' }}>
                                <p className="p-0 m-0">Tenants</p>
                                <FormControl sx={{ ...newPropsCss }} fullWidth>
                                    <Select
                                        fullWidth
                                        labelId="demo-simple-select-label"
                                        id="demo-simple-select"
                                        className='demo-simple-select'
                                        sx={{ ...newPropsCss, height: "2.4em" }}
                                        value={selectedTenantValue}
                                        onChange={handleTenantChange}
                                        //   onFocus={handleFocus}
                                        displayEmpty
                                        inputProps={{ 'aria-label': 'Without label' }}
                                        MenuProps={{ PaperProps: { sx: { maxHeight: 200 } } }}
                                    >
                                        <MenuItem value="" disabled>
                                            Select Tenant
                                        </MenuItem>
                                        {tenantOptions?.map((option, index) => (
                                            <MenuItem key={index} value={option} sx={{ ...newPropsCss }}>
                                                {option}
                                            </MenuItem>
                                        ))}
                                    </Select>
                                </FormControl>

                            </div>
                        </Grid> : null}

                        {azureSubscriptions ? <Grid item xs={12} sm={azureTenants ? 6 : 12} md={azureTenants ? 6 : 4} lg={azureTenants ? 3 : 4} xl={azureTenants ? 3 : 4} className='d-flex justify-content-center'>
                            <div style={{ width: '100%' }}>
                                <p className="p-0 m-0">Subscriptions</p>
                                <FormControl sx={{ ...newPropsCss }} fullWidth>
                                    <Select
                                        fullWidth
                                        labelId="demo-simple-select-label"
                                        id="demo-simple-select"
                                        className='demo-simple-select'
                                        sx={{ ...newPropsCss, height: "2.4em" }}
                                        value={azureSubscriptionValue}
                                        onChange={handleSubscriptionChange}
                                        //   onFocus={handleFocus}
                                        displayEmpty
                                        inputProps={{ 'aria-label': 'Without label' }}
                                        MenuProps={{ PaperProps: { sx: { maxHeight: 200 } } }}
                                    >
                                        <MenuItem value="" disabled>
                                            Select Subscription
                                        </MenuItem>
                                        {subscriptionOptions?.map((option, index) => (
                                            <MenuItem key={index} value={option} sx={{ ...newPropsCss }}>
                                                {option}
                                            </MenuItem>
                                        ))}
                                    </Select>
                                </FormControl>

                            </div>
                        </Grid> : null}

                        <Grid item xs={12} sm={azureSubscriptions ? azureTenants ? 6 : 12 : 12} md={azureSubscriptions ? azureTenants ? 6 : 4 : 6} lg={azureTenants ? 3 : 4} xl={azureTenants ? 3 : 4} className='d-flex justify-content-center'>

                            {props.children}
                        </Grid>

                        <Grid item xs={12} sm={azureSubscriptions ? azureTenants ? 6 : 12 : 12} md={azureSubscriptions ? azureTenants ? 6 : 4 : 6} lg={azureTenants ? 3 : 4} xl={azureTenants ? 3 : 4} className='d-flex justify-content-center'>
                            <div style={{ width: '100%' }}>
                                <p className="p-0 m-0">Duration</p>
                                <DurationSelector
                                    handleMonthChange={handleMonthChange}
                                    months={months}
                                    setDateRange={setDateRange}
                                    setCalling={setCalling}
                                    calling={calling}
                                />
                            </div>
                        </Grid>
                    </Grid>
                </Box>
            </Card>
        </>
    )
}

export default BillingInformationCard