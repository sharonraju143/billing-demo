import * as React from 'react';
import { Grid } from '@mui/material';
import { BarChart, Bar, Rectangle, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';

export default function BarsDataset({ data, height, width, barLineSize }) {
  if (!data) return null;

  const extractData = (data, key) => {
    return data?.monthlyTotalAmounts ? data?.monthlyTotalAmounts[0]?.[key]?.toFixed(2) : 0;
  };

  const extractData1 = (data, key) => {
    return (data?.monthlyTotalAmounts && key) ? key : '';
  };

  const dataset = [
    {
      AWS: extractData(data.awsData, Object.keys(data.awsData.monthlyTotalAmounts[0])[0]),
      Azure: extractData(data.azureData, Object.keys(data.azureData.monthlyTotalAmounts[0])[0]),
      GCP: extractData(data.gcpData, Object.keys(data.gcpData.monthlyTotalAmounts[0])[0]),
      name: extractData1(data.awsData, Object.keys(data.awsData.monthlyTotalAmounts[0])[0]),
    },
  ];

  return (
    <>
      {/* <Grid sx={{ px: 2, py: 4, m: 2 }} item xs={12} md={6} lg={6}> */}
      <ResponsiveContainer width="100%" height={height}>
        <BarChart
          width={'100%'}
          height={height}
          data={dataset}
          margin={{
            top: 5,
            right: 10,
            left: -10,
            bottom: 5,
          }}
        >
          <CartesianGrid horizontal={true} vertical={false} />
          <XAxis axisLine={false} dataKey="name"
            //  axisLine={{ stroke: 'black' }} 
            tick={{ fontSize: 11 }} />
          <YAxis axisLine={false}
            //  axisLine={{ stroke: 'black' }}
            tick={{ fontSize: 11 }} />
          <Tooltip />
          <Legend />
          <Bar dataKey="AWS" fill="#10B981" barSize={barLineSize}
          // activeBar={<Rectangle fill="pink" stroke="blue" />} 
          />
          <Bar dataKey="Azure" fill="#FE6476" barSize={barLineSize}
          // activeBar={<Rectangle fill="gold" stroke="purple" />}
          />
          {/* Uncomment the following lines if you want to include the 'gcp' bar */}
          <Bar dataKey="GCP" fill="#FEA37C" barSize={barLineSize}
          // activeBar={<Rectangle fill="gold" stroke="purple" />} 
          />
        </BarChart>
      </ResponsiveContainer>
      {/* </Grid> */}
    </>
  );
}