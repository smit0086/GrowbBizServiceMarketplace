import { initializeApp } from "firebase/app";
import { getStorage } from "firebase/storage";
import "firebase/storage";

const firebaseConfig =
{
  type: "service_account",
  project_id: "growbiz-csci5308",
  private_key_id: "f208d508ca6ee2070c4652313351b2dbf8e64ba4",
  private_key: "-----BEGIN PRIVATE KEY-----\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCzWu5pamPfhHAh\n2orQjZF3A1+9EmMO4ULrWMgFuaizms+qS8HgUclzYl4pgUwEt6glAcfzZ8VRIPtt\n5W9y8m1Fq+MbKraAftTysXsAFu2KTt0AEcn4IyMAHjwRCWmAVQdx9yqiQVZIHrZ0\naOwoaJp9npgkkH2Dv6mJCsoKAAht2Pm8VEQtoMqZAkWDdKbUIXDMk8g8X5qe13wi\njPhYUUI8NoVsjDg8ba+vUZ5CgqRwHL1A10MhogJOiYlpess4P1oaLRECyNJOyYG5\nNncT29BwIKuGO7YWEddGNZxlsqWTFzdcJtqcgAzelNP/afg45zA77KGkWPQtHZ5V\nWj0HhiH7AgMBAAECggEADVYAjjQL8w287Ld8RN+R5mCI2N0qImwbdrZmQQ65/YjQ\nUiBJ7S0Hpp201K85gMxLI11Licy4fjzOlTBPJiPifiC2xUlawpkrqlQKCkqqJPOS\nOTQZaWrralHVM4sYniM0Z4PyVpg3Gyu1c1ht3t9QRXDqbSWVP5ubtoXn2fCAr/In\nSft3BLIsY5lAeEvhUiXSluJqCcsZl3gUjlE8K2K6irt+OJDQnm8ri2RxsYOQU0Yz\nxAFdPueVy8VfXzG7jW+Xr5mA69h99/3Uy9fXlLchBcwkrx5JTEaaw2WqTmoIp/WW\nWSjhXg7eNQLWJi8KN0WmmleaicBdY04Uxbtq4k+88QKBgQDkIFU4hL+0vyT7MpHp\njoU4sPSmiItynhtTDAqSqXXa3Ne8yc8we+evZa9Lk6rIUYsgb444KoXWrRe3+2Ls\nqZxB91/VepmSO/n4kCBUocI4w2wFHjskIO2Hxq6h7jNj5NSVJbw0MhtywaVx5SH3\nijtzd1LX5TWHnZ71LSTD8obBkwKBgQDJRRCXjN5L6762MVjAbCm1QpoXqFECnvxb\nL1EKtqMyIYkTi+/Ez2bZb2gptVgNWkXNqeCyReuk92HJoAxFx0LzMbYkgOnBztd6\nyH6n2o21C5LzQBPhhINlTMGsw6dxEStzJ7HpWP3iJN2Qzf3Nmj3ydV44avvA189Z\neORGZoz++QKBgQCTpRmC6ufoY43bxm8JamRuvJia7+TQ2eN1bGWrZmIQtL4sQbEH\nACqDKpy5nWj7HUueQpUZZOwadQzU1q6/3UZ0q1YrJJ4gNxEh/qXuIINDmbdf2KEO\nUQFqzvxXcYNxM7kijrmwTn3VV99sR5AkViMEKEZF1ONAUpN+uykAaQcuYQKBgGnN\nT4qLN7QrSgaWbDRC563sQ/qZtXQuRTRd7Dk4hxNsQ5Wt4y3PZR56DQSX2qCySq1x\nVrgAP35puNL3ulvYAp67DCemSLtj0ywtRz9PiVyZp/cNQSY0tanDrq/17ghm4TNY\ns5KaAGQnwv1C+99f7J6PCieYPZ1DFTHQdhd4AzhpAoGAYW5owVexdA4QPH9RAILU\numMQs3HlMjZn8tcda+oiMBKB+fIH9cwFLGgwxmgcFYXMBvb40zvhn2xF1L2W1/RN\n3ATTALmPeozeFFPwWKGiXvb3WUgVMJune7aLUvBIkFNdTn2y4iKAKELOG/pgXedX\n30Nhn5EhgunnkXxoWsUhGUs=\n-----END PRIVATE KEY-----\n",
  client_email: "firebase-adminsdk-sk7nb@growbiz-csci5308.iam.gserviceaccount.com",
  client_id: "110193472832375791099",
  auth_uri: "https://accounts.google.com/o/oauth2/auth",
  token_uri: "https://oauth2.googleapis.com/token",
  auth_provider_x509_cert_url: "https://www.googleapis.com/oauth2/v1/certs",
  client_x509_cert_url: "https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-sk7nb%40growbiz-csci5308.iam.gserviceaccount.com",
  universe_domain: "googleapis.com",
  storageBucket: "growbiz-csci5308.appspot.com"
};

const app = initializeApp(firebaseConfig);

export const storage = getStorage(app);