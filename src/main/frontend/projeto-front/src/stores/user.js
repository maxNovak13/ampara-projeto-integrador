import { defineStore } from 'pinia';
import axios from 'axios';

export const useUserStore = defineStore('user', {
    state: () => ({
        userData: null,
        token: localStorage.getItem('token') || null,
    }),
    actions: {
        async login(token) {
            this.token = token;
            localStorage.setItem('token', token);

            // Extrai UUID do token
            const payload = JSON.parse(atob(token.split('.')[1]));
            const uuid = payload.uuid;
            localStorage.setItem('uuid', uuid);

            // Busca perfil do usuário
            await this.fetchUserProfile(uuid);

            // Se o usuário não está ativo, desloga e lança erro
            if (!this.userData || this.userData.situacao !== 'ATIVO') {
                this.logout();
                throw new Error('Usuário não ativo');
            }

            // console.log('Usuário logado com sucesso:', this.userData, 'Token:', this.token);
        },

        async fetchUserProfile(uuid) {
            if (!this.token || !uuid) return;

            try {
                const response = await axios.get(`http://localhost:8080/ampara/profissional/uuid/${uuid}`, {
                    headers: {
                        Authorization: `Bearer ${this.token}`,
                    },
                });

                this.userData = response.data;
                localStorage.setItem('profissional', JSON.stringify(response.data));
            } catch (error) {
                this.logout();
            }
        },

        logout() {
            this.token = null;
            this.userData = null;
            localStorage.removeItem('token');
            localStorage.removeItem('profissional');
            localStorage.removeItem('uuid');
        },
    },
});
