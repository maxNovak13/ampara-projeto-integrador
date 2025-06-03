<template>
  <div class="p-6 max-w-5xl mx-auto">
    <div class="max-w-5xl mx-auto border border-gray-300 mt-5 mb-10 p-8 bg-white rounded-lg shadow-md">
      <h1 class="text-3xl font-bold text-purple-800 mb-8 text-center">Perfil da Mulher</h1>

      <div v-if="mulher">
        <!-- Grid com 3 colunas: pessoais, complementares e endereço -->
        <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
          <!-- Dados pessoais -->
          <div>
            <h2 class="text-lg font-semibold text-purple-700 mb-3"> Dados Pessoais</h2>
            <p class="mb-3"><strong>Nome: <br/> </strong> {{ mulher.nome }}</p>
            <p class="mb-3"><strong>Nascimento: <br/> </strong> {{ formatarData(mulher.dataNascimento) }}</p>
            <p class="mb-3"><strong>CPF: <br/> </strong> {{ mulher.cpf }}</p>
            <p><strong>Telefone: <br/> </strong> {{ mulher.telefone }}</p>
          </div>

          <!-- Informações complementares -->
          <div>
            <h2 class="text-lg font-semibold text-purple-700 mb-3"> Informações Complementares</h2>
            <p class="mb-3"><strong>Profissão: <br/> </strong> {{ mulher.profissao || '-' }}</p>
            <p class="mb-3"><strong>Nome da Mãe: <br/> </strong> {{ mulher.nomeMae }}</p>
            <p class="mb-3"><strong>Escolaridade: <br/> </strong> {{ mulher.escolaridade }}</p>
            <p><strong>Estado Civil: <br/> </strong> {{ mulher.estadoCivil }}</p>
          </div>

          <!-- Endereço -->
          <div>
            <h2 class="text-lg font-semibold text-purple-700 mb-3"> Endereço</h2>
            <p class="mb-3"><strong>Rua: <br/> </strong> {{ mulher.endereco.rua }}</p>
            <p class="mb-3"><strong>Nº: <br/> </strong> {{ mulher.endereco.numero }}</p>
            <p class="mb-3"><strong>Bairro: <br/></strong> {{ mulher.endereco.bairro }}</p>
            <p><strong>Complemento: <br/> </strong> {{ mulher.endereco.complemento || '-' }}</p>
          </div>
        </div>
      </div>

      <div v-else class="text-center text-gray-600">
        Carregando informações...
      </div>
      <div class="mt-6 flex justify-end">
        <button class="bg-purple-200 text-gray-800 py-3 px-6 rounded hover:bg-gray-400 transition text-left">
          Editar
        </button>
      </div>
    </div>
    <BotaoVoltar class="mt-6" />
  </div>
</template>

<script setup>
  import { ref, onMounted } from 'vue'
  import { useRoute } from 'vue-router'
  import axios from 'axios'
  import BotaoVoltar from "../components/BotaoVoltar.vue";

  const route = useRoute()
  const mulher = ref(null)

  onMounted(async () => {
    const uuid = route.params.uuid
    try {
      const { data } = await axios.get(`http://localhost:8080/ampara/mulher/uuid/${uuid}`)
      mulher.value = data
    } catch (error) {
      console.error('Erro ao carregar perfil:', error)
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