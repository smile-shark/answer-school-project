/* eslint-disable */
import axios from 'axios'

const axiosInstance = axios.create({
    timeout: 60*1000, // 请求超时时间
    headers: {
        'Content-Type': 'application/json',
        // 其他默认的头部信息
    }
});

// 添加请求拦截器
axiosInstance.interceptors.request.use(
    config => {
        const jwtToken = localStorage.getItem('token')
        if (jwtToken) {
            config.headers['token'] = jwtToken
        }
        return config;
    },
    error => {
        return Promise.reject(error)
    }
);

export default axiosInstance
