import { ref } from 'vue'
//responsável pela l[ogica de autenticação
const isLoggedIn = ref(checkIsLoggedIn())
const profissional = ref(getProfissional())

function checkIsLoggedIn() {//se precisar
    const data = localStorage.getItem('profissional')
    if (!data) return false
    try {
        const parsed = JSON.parse(data)
        return parsed.situacao === 'ATIVO'
    } catch (e) {
        return false
    }
}

function getProfissional() {
    const data = localStorage.getItem('profissional')
    return data ? JSON.parse(data) : null
}

export function login(dadosProfissional) {
    localStorage.setItem('profissional', JSON.stringify(dadosProfissional))
    isLoggedIn.value = true
    profissional.value = dadosProfissional
}

export function logout() {
    localStorage.removeItem('profissional')
    isLoggedIn.value = false
    profissional.value = null
}

export function useAuth() {
    return {
        isLoggedIn,
        profissional,
        login,
        logout
    }
}
