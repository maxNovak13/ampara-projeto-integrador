import { defineStore } from 'pinia'
import api from '../services/api.js'

export const useAuthStore = defineStore('auth', {
    state: () => ({
        token: localStorage.getItem('token') || '',
        user: null,
    }),
    getters: {
        isLoggedIn: (state) => !!state.token,
    },
    actions: {
        async login(email, senha) {
            const response = await api.post('/login', { email, senha })
            this.token = response.data.token
            localStorage.setItem('token', this.token)
        },
        logout() {
            this.token = ''
            this.user = null
            localStorage.removeItem('token')
        },
    },
})
