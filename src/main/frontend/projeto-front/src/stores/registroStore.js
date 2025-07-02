import { defineStore } from 'pinia'
import axios from 'axios'
import { useUserStore } from './user'

export const useRegistroStore = defineStore('registro', {
    state: () => ({
        registro: {
            dataOcorrido: '',
            depoimento: '',
            violencia: false,
            tipoViolencia: '',
            relacaoVitima: '',
            lugar: '',
            conduta: '',
            encaminhamento: '',
            descricao: '',
            mulher: { uuid: '' },
            local: { id: null },
            profissional: { uuid: '' },
            agressores: []
        }
    }),
    actions: {
        setDadosIniciais(dados) {
            this.registro = { ...this.registro, ...dados }
        },
        adicionarAgressor(agressor) {
            this.registro.agressores.push(agressor)
        },
        async enviar() {
            const userStore = useUserStore()
            const token = userStore.token

            try {
                await axios.post(
                    'http://localhost:8080/ampara/registro',
                    this.registro,
                    {
                        headers: {
                            'Authorization': `Bearer ${token}`,
                            'Content-Type': 'application/json'
                        }
                    }
                )
            } catch (error) {
                console.error('Erro ao enviar registro:', error)
                throw error
            }
        },
        resetar() {
            this.$reset()
        }
    }
})