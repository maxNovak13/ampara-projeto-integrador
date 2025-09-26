import { defineStore } from 'pinia';
import api from '../services/api';

export const useUserStore = defineStore('user', {
    state: () => ({
        userData: null,
        token: localStorage.getItem('token') || null,
    }),
    actions: {
        async login(token) {
            this.token = token;
            localStorage.setItem('token', token);

            await this.fetchUserProfile();

            if (!this.userData || this.userData.situacao !== 'ATIVO') {
                this.logout();
                throw new Error('Usuário não ativo');
            }

            // console.log('Usuário logado com sucesso:', this.userData, 'Token:', this.token);
        },

        async fetchUserProfile() {
            if (!this.token) return;

            try {
                const response = await api.get('/profissional/logado'); // token será adicionado pelo interceptor
                this.userData = response.data;
                localStorage.setItem('profissional', JSON.stringify(response.data));
            } catch (error) {
                //console.error('Erro ao buscar perfil:', error);
                this.logout();
                throw error;
            }
        },

        initialize() {
            const token = localStorage.getItem('token');
            const user = localStorage.getItem('profissional');

            if (token && user) {
                const payload = JSON.parse(atob(token.split('.')[1]));
                const exp = payload.exp * 1000; // segundos → ms
                const now = Date.now();

                if (exp > now) {
                    // Token ainda válido
                    this.token = token;
                    this.userData = JSON.parse(user);
                } else {
                    this.logout(); // Token expirado
                }
            }
        },

        logout() {
            this.token = null;
            this.userData = null;
            localStorage.removeItem('token');
            localStorage.removeItem('profissional');
        },
    },
});
