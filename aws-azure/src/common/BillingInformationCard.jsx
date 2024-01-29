import React from 'react'
import { Card, Grid, Box } from "@mui/material";
import DurationSelector from "../components/DurationSelector";
const BillingInformationCard = (props) => {
    const { handleMonthChange, months, setDateRange, setCalling, calling } = props;
    console.log("props", props)
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
                        <Grid item xs={12} lg={4} xl={4}>
                            <div className="h4 fw-bold">Billing Information</div>
                        </Grid>
                        <Grid item xs={12} sm={12} md={6} lg={4} xl={4} className='d-flex justify-content-center'>
                            {/* <div>
                                <p className="p-0 m-0">Service</p> */}
                            {/* <ServiceSelector
                        service={service}
                        handleServiceChange={handleServiceChange}
                      /> */}
                            {props.children}
                            {/* </div> */}
                        </Grid>

                        <Grid item xs={12} sm={12} md={6} lg={4} xl={4} className='d-flex justify-content-center'>
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