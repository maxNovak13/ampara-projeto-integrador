<template>
  <div class="max-w-5xl border border-gray-300 mx-auto mt-5 mb-5 p-8 bg-white rounded shadow-lg">
    <h1 class="text-3xl font-bold mb-8 text-center text-purple-800">Cadastro da mulher</h1>

    <!-- Formulário de cadastro da mulher -->
    <form @submit.prevent="handleCadastro" class="grid grid-cols-1 md:grid-cols-2 gap-8">

      <!-- Lado esquerdo do formulário-->
      <div class="flex flex-col gap-6">
        <div>
          <label class="mb-1 text-gray-700 font-medium">Nome completo</label>
          <input
              v-model="mulher.nome"
              type="text"
              required
              maxlength="100"
              class="border border-gray-300 rounded px-5 py-3 focus:outline-none focus:ring-2 focus:ring-purple-500 w-full" />
          <p v-if="erros.nome" class="text-red-500 text-sm mt-1">{{ erros.nome }}</p>
        </div>

        <div class="flex gap-4">
          <div class="w-1/2">
            <label class="mb-1 text-gray-700 font-medium">Data de nascimento</label>
            <input
                v-mask="'##/##/####'"
                v-model="mulher.dataNascimento"
                type="text"
                required
                maxlength="10"
                class="border border-gray-300 rounded px-5 py-3 focus:outline-none focus:ring-2 focus:ring-purple-500 w-full"
            />
            <p v-if="erros.dataNascimento" class="text-red-500 text-sm mt-1">{{ erros.dataNascimento }}</p>
          </div>
          <div class="w-1/2">
            <label class="mb-1 text-gray-700 font-medium">CPF</label>
            <input
                v-mask="'###.###.###-##'"
                v-model="mulher.cpf"
                type="text"
                required
                maxlength="14"
                class="border border-gray-300 rounded px-5 py-3 focus:outline-none focus:ring-2 focus:ring-purple-500 w-full"
            />
            <p v-if="erros.cpf" class="text-red-500 text-sm mt-1">{{ erros.cpf }}</p>
          </div>
        </div>

        <div class="flex gap-4">
          <div class="w-1/2">
            <label class="mb-1 text-gray-700 font-medium">Telefone</label>
            <input
                v-mask="'(##) #####-####'"
                v-model="mulher.telefone"
                type="text"
                required
                maxlength="15"
                class="border border-gray-300 rounded px-5 py-3 focus:outline-none focus:ring-2 focus:ring-purple-500 w-full"
            />
            <p v-if="erros.telefone" class="text-red-500 text-sm mt-1">{{ erros.telefone }}</p>
          </div>
          <div class="w-1/2">
            <label class="mb-1 text-gray-700 font-medium">Profissão</label>
            <input
                v-model="mulher.profissao"
                type="text"
                required
                maxlength="50"
                class="border border-gray-300 rounded px-5 py-3 focus:outline-none focus:ring-2 focus:ring-purple-500 w-full" />
            <p v-if="erros.profissao" class="text-red-500 text-sm mt-1">{{ erros.profissao }}</p>
          </div>
        </div>

        <div>
          <label class="mb-1 text-gray-700 font-medium">Nome completo da mãe</label>
          <input
              v-model="mulher.nomeMae"
              type="text"
              required
              maxlength="100"
              class="border border-gray-300 rounded px-5 py-3 focus:outline-none focus:ring-2 focus:ring-purple-500 w-full" />
          <p v-if="erros.nomeMae" class="text-red-500 text-sm mt-1">{{ erros.nomeMae }}</p>
        </div>

        <div>
          <label class="mb-1 text-gray-700 font-medium">Escolaridade</label>
          <select v-model="mulher.escolaridade" required class="border border-gray-300 rounded px-5 py-3 focus:outline-none focus:ring-2 focus:ring-purple-500 w-full">
            <option disabled value="">Selecione</option>
            <option>Analfabeta</option>
            <option>Ensino Fundamental Incompleto</option>
            <option>Ensino Fundamental Completo</option>
            <option>Ensino Médio Incompleto</option>
            <option>Ensino Médio Completo</option>
            <option>Superior Incompleto</option>
            <option>Superior Completo</option>
            <option>Pós-graduação</option>
          </select>
          <p v-if="erros.escolaridade" class="text-red-500 text-sm mt-1">{{ erros.escolaridade }}</p>
        </div>
      </div>

      <!-- Lado direito -->
      <div class="flex flex-col gap-6">
        <div class="mb-1">
          <label class="text-gray-700 font-medium">Estado civil</label>
          <select v-model="mulher.estadoCivil" required class="border border-gray-300 rounded px-5 py-3 focus:outline-none focus:ring-2 focus:ring-purple-500 w-full">
            <option disabled value="">Selecione</option>
            <option>Solteira</option>
            <option>Casada</option>
            <option>Divorciada</option>
            <option>Viúva</option>
            <option>União Estável</option>
          </select>
          <p v-if="erros.estadoCivil" class="text-red-500 text-sm mt-1">{{ erros.estadoCivil }}</p>
        </div>

        <div>
          <h2 class="text-lg font-semibold text-purple-700 mb-5 text-center">Endereço</h2>
          <div class="flex gap-4 mb-4">
            <div class="w-2/3 mb-4">
              <label class="text-gray-700 font-medium">Rua</label>
              <input
                  v-model="mulher.endereco.rua"
                  type="text"
                  required
                  maxlength="100"
                  class="border border-gray-300 rounded px-5 py-3 focus:outline-none focus:ring-2 focus:ring-purple-500 w-full" />
              <p v-if="erros['endereco.rua']" class="text-red-500 text-sm mt-1">{{ erros['endereco.rua'] }}</p>
            </div>
            <div class="w-1/3 mb-4">
              <label class="text-gray-700 font-medium">Número</label>
              <input
                  v-model="mulher.endereco.numero"
                  type="text"
                  required
                  maxlength="15"
                  class="border border-gray-300 rounded px-5 py-3 focus:outline-none focus:ring-2 focus:ring-purple-500 w-full" />
              <p v-if="erros['endereco.numero']" class="text-red-500 text-sm mt-1">{{ erros['endereco.numero'] }}</p>
            </div>
          </div>

          <div class="mb-7">
            <label class="text-gray-700 font-medium">Bairro</label>
            <input
                v-model="mulher.endereco.bairro"
                type="text"
                required
                maxlength="80"
                class="border border-gray-300 rounded px-5 py-3 focus:outline-none focus:ring-2 focus:ring-purple-500 w-full" />
            <p v-if="erros['endereco.bairro']" class="text-red-500 text-sm mt-1">{{ erros['endereco.bairro'] }}</p>
          </div>

          <div>
            <label class="text-gray-700 font-medium">Complemento</label>
            <input
                v-model="mulher.endereco.complemento"
                type="text"
                required
                maxlength="100"
                class="border border-gray-300 rounded px-5 py-3 focus:outline-none focus:ring-2 focus:ring-purple-500 w-full" />
            <p v-if="erros['endereco.complemento']" class="text-red-500 text-sm mt-1">{{ erros['endereco.complemento'] }}</p>
          </div>
        </div>
      </div>

      <div class="col-span-1 md:col-span-2 flex justify-between">
        <BotaoCancelar/>
        <button type="submit" class="bg-purple-700 text-white py-3 px-10 rounded hover:bg-purple-800 transition">
          Cadastrar
        </button>
      </div>
    </form>
  </div>
</template>

<script setup>
  import { reactive, ref } from 'vue'
  import axios from 'axios'
  import { useRouter } from 'vue-router'
  import BotaoCancelar from "../components/BotaoCancelar.vue";

  const router = useRouter()
  const erros = reactive({})

  const mulher = reactive({
    nome: '',
    dataNascimento: '',
    cpf: '',
    telefone: '',
    profissao: '',
    nomeMae: '',
    escolaridade: '',
    estadoCivil: '',
    endereco: {
      rua: '',
      numero: '',
      bairro: '',
      complemento: ''
    }
  })

  async function handleCadastro() {
    try {
      Object.keys(erros).forEach(key => delete erros[key])

      // Obter token do localStorage
      const token = localStorage.getItem('token')

      const response = await axios.post(
          'http://localhost:8080/ampara/mulher',
          mulher,
          {
            headers: {
              Authorization: `Bearer ${token}`
            }
          }
      )
      await router.push(`/perfil-mulher/${response.data.uuid}`)
    } catch (err) {
      if (err.response && err.response.data && typeof err.response.data === 'object') {
        Object.assign(erros, err.response.data)
        console.error(err.response?.data || err.message)
      } else {
        erros.geral = 'Erro desconhecido ao cadastrar mulher.'
      }
    }
  }
</script>

<style scoped>
</style>
