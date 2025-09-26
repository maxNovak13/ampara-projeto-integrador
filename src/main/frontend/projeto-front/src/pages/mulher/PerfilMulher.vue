<template>
  <div class="p-6 max-w-5xl mx-auto">
    <div class="max-w-4xl mx-auto border border-gray-300 mt-5 mb-2 p-5 px-10 pb-10 bg-white rounded-lg shadow-md">


      <div v-if="mulher">
        <h1 class="text-3xl font-bold text-purple-800 mb-8 text-center">{{ mulher.nome }}</h1>
        <div class="grid grid-cols-1 md:grid-cols-3 gap-5">

          <!-- Dados pessoais -->
          <div class="space-y-4">
            <h2 class="text-lg font-semibold text-purple-700 border-b pb-1">Dados Pessoais</h2>
            <div class="grid grid-cols-1 sm:grid-cols-2 gap-3 text-sm text-gray-800">
              <div>
                <p class="font-medium text-gray-700">Nome</p>
                <p>{{ mulher.nome }}</p>
              </div>
              <div>
                <p class="font-medium text-gray-700">Nascimento</p>
                <p>{{ formatarData(mulher.dataNascimento) }}</p>
              </div>
              <div>
                <p class="font-medium text-gray-700">CPF</p>
                <p>{{ mulher.cpf }}</p>
              </div>
              <div>
                <p class="font-medium text-gray-700">Telefone</p>
                <p>{{ mulher.telefone }}</p>
              </div>
            </div>
          </div>

          <!-- Informações complementares -->
          <div class="space-y-4">
            <h2 class="text-lg font-semibold text-purple-700 border-b pb-1">Informações Complementares</h2>
            <div class="grid grid-cols-1 sm:grid-cols-2 gap-3 text-sm text-gray-800">
              <div>
                <p class="font-medium text-gray-700">Profissão</p>
                <p>{{ mulher.profissao || '-' }}</p>
              </div>
              <div>
                <p class="font-medium text-gray-700">Estado Civil</p>
                <p>{{ mulher.estadoCivil }}</p>
              </div>
              <div>
                <p class="font-medium text-gray-700">Escolaridade</p>
                <p>{{ mulher.escolaridade }}</p>
              </div>
              <div>
                <p class="font-medium text-gray-700">Nome da Mãe</p>
                <p>{{ mulher.nomeMae }}</p>
              </div>
            </div>
          </div>

          <!-- Endereço -->
          <div class="space-y-4">
            <h2 class="text-lg font-semibold text-purple-700 border-b pb-1">Endereço</h2>
            <div class="text-sm text-gray-800">
              <p class="font-medium text-gray-700 mb-1">Endereço completo</p>
              <p>
                {{ mulher.endereco.rua }}, {{ mulher.endereco.numero }} - {{ mulher.endereco.bairro }},
                {{ mulher.endereco.complemento || '-' }}
              </p>
            </div>
          </div>

        </div>
      </div>

      <div v-else class="text-center text-gray-600">
        Carregando informações...
      </div>
    </div>

    <TabelaRegistros v-if="mulher" :uuidMulher="mulher.uuid" />

    <button
        @click="router.push({ name: 'CadastrarRegistro', params: { uuidMulher: mulher.uuid } })"
        class="max-w-4xl w-full px-auto mx-auto block bg-purple-500 text-white px-4 py-3 rounded hover:bg-purple-700 transition"
    >
      Adicionar registro
    </button>

    <BotaoVoltar class="mt-6" />
  </div>
</template>

<script setup>
  import { ref, onMounted } from 'vue'
  import {useRoute, useRouter} from 'vue-router'
  import BotaoVoltar from "../../components/BotaoVoltar.vue";
  import TabelaRegistros from "../../components/TabelaRegistros.vue";
  import api from '../../services/api.js'

  const router = useRouter()
  const route = useRoute()
  const mulher = ref(null)

  onMounted(async () => {
    const uuid = route.params.uuid
    try {
      const { data } = await api.get(`/mulher/uuid/${uuid}`)
      mulher.value = data
    } catch (error) {
      //console.error('Erro ao carregar perfil:', error)
    }
  })

  function formatarData(dataISO) {
    if (!dataISO) return "-"
    const [ano, mes, dia] = dataISO.split("-")
    return `${dia}/${mes}/${ano}`
  }
</script>

<style scoped>

</style>