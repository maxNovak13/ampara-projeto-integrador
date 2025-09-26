import axios from 'axios';
import { useUserStore } from '../stores/user';

const api = axios.create({
    baseURL: 'http://localhost:8080/ampara',
});

// Interceptor de requisição: adiciona token automaticamente
api.interceptors.request.use(
    config => {
        const userStore = useUserStore();
        if (userStore.token) {
            config.headers.Authorization = `Bearer ${userStore.token}`;
        }
        return config;
    },
    error => {
        return Promise.reject(error);
    }
);

// Interceptor de resposta: trata erros globais (ex: token expirado)
api.interceptors.response.use(
    response => response,
    error => {
        if (error.response && error.response.status === 401) {
            const userStore = useUserStore();
            userStore.logout();
            router.push('/login')
        }
        return Promise.reject(error);
    }
);

export default api;
