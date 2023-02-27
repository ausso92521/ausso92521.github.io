'use client'
import axios from 'axios';

const api = axios.create({
  withCredentials: true,
  baseURL: 'https://localhost:443/api/v1'
});

export default api;