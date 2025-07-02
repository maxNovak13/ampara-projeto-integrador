import { defineStore } from 'pinia'
import axios from 'axios'

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
            const response = await axios.post('http://localhost:8080/ampara/login', { email, senha })
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
