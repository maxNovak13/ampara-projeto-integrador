<template>
  <div class="max-w-lg mx-auto border border-gray-300 mt-5 mb-5 p-8 bg-white rounded shadow-lg">
    <h1 class="text-3xl font-bold mb-8 text-center">Adicionar local</h1>

    <!-- Formulário de cadastro de locais -->
    <form @submit.prevent="handleCadastro" class="flex flex-col gap-6">
      <div class="flex flex-col">
        <label for="nome" class="mb-1 text-gray-700 font-medium">Nome do local</label>
        <input
            id="nome"
            v-model="local.nome"
            type="text"
            placeholder="Ex: Casa Abrigo Aurora"
            maxlength="100"
            required
            class="border border-gray-300 rounded px-5 py-3 focus:outline-none focus:ring-2 focus:ring-purple-500"
        />
      </div>

      <div class="flex gap-4">
        <div class="flex flex-col w-2/3">
          <label for="rua" class="mb-1 text-gray-700 font-medium">Rua</label>
          <input
              id="rua"
              v-model="local.endereco.rua"
              type="text"
              maxlength="100"
              placeholder="Ex: Rua das Flores"
              required
              class="border border-gray-300 rounded px-5 py-3 focus:outline-none focus:ring-2 focus:ring-purple-500"
          />
        </div>

        <div class="flex flex-col w-1/3">
          <label for="numero" class="mb-1 text-gray-700 font-medium">Número</label>
          <input
              id="numero"
              v-model="local.endereco.numero"
              type="text"
              maxlength="15"
              placeholder="Ex: 123"
              required
              class="border border-gray-300 rounded px-5 py-3 focus:outline-none focus:ring-2 focus:ring-purple-500"
          />
        </div>
      </div>

      <div class="flex flex-col">
        <label for="bairro" class="mb-1 text-gray-700 font-medium">Bairro</label>
        <input
            id="bairro"
            v-model="local.endereco.bairro"
            type="text"
            placeholder="Ex: Centro"
            maxlength="80"
            required
            class="border border-gray-300 rounded px-5 py-3 focus:outline-none focus:ring-2 focus:ring-purple-500"
        />
      </div>

      <div class="flex flex-col">
        <label for="complemento" class="mb-1 text-gray-700 font-medium">Complemento</label>
        <input
            id="complemento"
            v-model="local.endereco.complemento"
            type="text"
            placeholder="Ex: Próximo à praça"
            maxlength="100"
            class="border border-gray-300 rounded px-5 py-3 focus:outline-none focus:ring-2 focus:ring-purple-500"
        />
      </div>

      <div class="flex justify-between">
        <BotaoCancelar destino="/gerenciar-local" />
        <button
            type="submit"
            class="bg-purple-700 text-white py-3 px-7 rounded hover:bg-purple-800 transition"
        >
          Salvar
        </button>
      </div>
    </form>
  </div>
</template>

<script setup>
import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useToast } from 'vue-toastification'
import api from '../../services/api.js'
import BotaoCancelar from '../../components/BotaoCancelar.vue'

const toast = useToast()
const router = useRouter()

const local = reactive({
  nome: '',
  endereco: {
    rua: '',
    numero: '',
    bairro: '',
    complemento: ''
  }
})

async function handleCadastro() {
  try {
    await api.post('/local', local)

    toast.success('Local cadastrado com sucesso!')
    setTimeout(() => {
      router.push('/gerenciar-local')
    }, 2000)
  } catch (error) {
    //console.error(error)
    toast.error('Erro ao cadastrar local.')
  }
}
</script>

<style scoped>
</style>
