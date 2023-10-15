import React, { useState, useEffect } from 'react';
import {
    Table,
    TableBody,
    TableCaption,
    TableCell,
    TableHead,
    TableHeader,
    TableRow,
  } from "@/components/ui/table"
import axios from 'axios';

const categoriesTable = () => {
  const [data, setData] = useState([]);

  useEffect(() => {
    // Make a GET request to your API here
    axios.get('/admin/allCategories')
      .then((response) => {
        setData(response.data);
      })
      .catch((error) => {
        console.error('Error fetching data:', error);
      });
  }, []); // The empty dependency array ensures this effect runs once after the component mounts

  return (
    <div>
      <table>
        <thead>
          <tr>
            <th>Category ID</th>
            <th>Category Name</th>
            <th>Tax %</th>
          </tr>
        </thead>
        <tbody>
          {data.map((item) => (
            <tr key={item.id}>
              <td>{item.id}</td>
              <td>{item.name}</td>
              <td>{item.tax}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default DataTable;
