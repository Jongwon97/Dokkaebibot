import React, { useState, useEffect } from 'react';
import { Chart } from 'primereact/chart';
import { getGraphData } from '../../../client/analysis';
import graphDataType from './GraphDataInterface';

function DataGraph({ graphData }: { graphData: undefined | graphDataType }) {
  const [chartData, setChartData] = useState({});
  const [chartOptions, setChartOptions] = useState({});

  useEffect(() => {
    const documentStyle = getComputedStyle(document.documentElement);
    const textColor = documentStyle.getPropertyValue('--text-color');
    const textColorSecondary = documentStyle.getPropertyValue('--text-color-secondary');
    const surfaceBorder = documentStyle.getPropertyValue('--surface-border');
    const data = {
      labels: graphData?.timeIndex,
      datasets: [
        {
          label: 'temperature',
          fill: false,
          borderColor: '#3575F3',
          yAxisID: 'temperature',
          tension: 0.4,
          data: graphData?.temperatureData,
        },
        {
          label: 'humidity',
          fill: false,
          borderColor: "#37BF7F",
          yAxisID: 'humidity',
          tension: 0.4,
          data: graphData?.humidityData,
        },
        {
          label: 'dust',
          fill: false,
          borderColor: "#F2D06C",
          yAxisID: 'dust',
          tension: 0.4,
          data: graphData?.dustData,
        },
      ],
    };
    const options = {
      stacked: false,
      maintainAspectRatio: false,
      aspectRatio: 0.6,
      plugins: {
        legend: {
          position: 'bottom',
          labels: {
            color: textColor,
          },
        },
      },
      scales: {
        x: {
          ticks: {
            display: false,
          },
          grid: {
            display: false,
            color: surfaceBorder,
          },
        },
        temperature: {
          type: 'linear',
          display: true,
          min: 10,
          max: 40,
          position: 'left',
          ticks: {
            color: textColorSecondary,
          },
          grid: {
            color: surfaceBorder,
          },
        },
        humidity: {
          type: 'linear',
          display: true,
          min: 0,
          max: 90,
          position: 'left',
          ticks: {
            stepSize: 30,
            color: textColorSecondary,
          },
          grid: {
            drawOnChartArea: false,
            color: surfaceBorder,
          },
        },
        dust: {
          type: 'linear',
          display: true,
          min: 0,
          max: 180,
          position: 'right',
          ticks: {
            stepSize: 30,
            color: textColorSecondary,
          },
          grid: {
            drawOnChartArea: false,
            color: surfaceBorder,
          },
        },
      },
    };

    setChartData(data);
    setChartOptions(options);
  }, [graphData]);

  return (
    <div className="card">
      <Chart type="line" data={chartData} options={chartOptions} />
    </div>
  );
}

export default DataGraph;
