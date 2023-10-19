"use client";

import React from 'react';
import { getServerSession } from 'next-auth';
import { authOptions } from '@/app/api/auth/[...nextauth]/route';

export async function CategoriesTable({ className, ...props }) {
  const session =  useSession();
  console.log(session.data.apiToken);
  const categoriesListResponse = await (
    await fetch(`${process.env.SERVER_ADDRESS}/admin/allCategories`, {
      method: "get",
      headers: {
        "Content-type": "application/json",
        Authorization: `Bearer ${session.apiToken}`,
      },
    })
  ).json();

// }

// const CategoriesTable = async () => {

//   const session = await getServerSession(authOptions);
//   const categoriesListResponse = await (
//     await fetch(`${process.env.SERVER_ADDRESS}/admin/allCategories`, {
//       method: "get",
//       headers: {
//         "Content-type": "application/json",
//         Authorization: `Bearer ${session.apiToken}`,
//       },
//     })
//   ).json();

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
          {categoriesListResponse.map((item) => (
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

export default CategoriesTable;
