<template>
  <div class="p-6 max-w-5xl mx-auto">
    <!-- Título -->
    <h1 class="text-3xl font-bold mb-6 text-purple-800">Locais cadastrados</h1>

    <!-- Tabela dos locais -->
    <div class="w-full max-w-4xl mx-auto">
      <table class="w-full border bg-white border-gray-200">
        <thead>
          <tr class="bg-purple-100 text-center text-purple-900">
            <th class="px-6 py-3 border-b border-gray-300">Nome</th>
            <th class="px-6 py-3 border-b border-gray-300">Endereço</th>
          </tr>
        </thead>
        <tbody>
          <tr
              v-for="(local, index) in locais"
              :key="index"
              class="hover:bg-purple-50 transition"
          >
            <td class="px-6 py-3 border-b border-gray-300">{{ local.nome }}</td>
            <td class="px-6 py-3 border-b border-gray-300">{{ formatarEndereco(local.endereco) }}</td>
          </tr>
        </tbody>
      </table>
      <br/>
      <!-- Botão adicionar lugar -->
      <button
          @click="router.push('/cadastrar-local')"
          class="w-full bg-purple-500 text-white px-4 py-3 rounded hover:bg-purple-700 transition"
      >
        Adicionar local
      </button>
    </div>
    <BotaoVoltar/>
  </div>
</template>

<script setup>
  import {ref, onMounted} from 'vue'
  import {useRouter} from 'vue-router'
  import axios from 'axios'
  import BotaoVoltar from "../components/BotaoVoltar.vue";

  const router = useRouter()
  const locais = ref([])
  const erro = ref(null)

  // Busca os locais da API ao montar o componente
  onMounted(async () => {
    try {
      const response = await axios.get('http://localhost:8080/ampara/local/listar')
      locais.value = response.data
    } catch (err) {
      erro.value = 'Erro ao carregar os locais.'
      console.error('Erro ao buscar locais:', err)
    }
  })

  // Mostra endereço formatado
  function formatarEndereco(endereco) {
    return `${endereco.rua}, ${endereco.numero} - ${endereco.bairro}` +
        (endereco.complemento ? `, ${endereco.complemento}` : '')
  }
</script>

<style scoped>

</style>