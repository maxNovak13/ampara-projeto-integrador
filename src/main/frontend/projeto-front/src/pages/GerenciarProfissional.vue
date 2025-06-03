<template>
  <div class="p-6 max-w-5xl mx-auto">
    <!-- Título -->
    <h1 class="text-3xl font-bold mb-6 text-purple-800">Profissionais cadastrados</h1>

    <!-- Tabela -->
    <div class="w-full max-w-4xl mx-auto">
      <table class="w-full border bg-white border-gray-200">
        <thead>
        <tr class="bg-purple-100 text-center text-purple-900">
          <th class="px-6 py-3 border-b border-gray-300">Nome</th>
          <th class="px-6 py-3 border-b border-gray-300">Profissão</th>
          <th class="px-6 py-3 border-b border-gray-300">Registro</th>
          <th class="px-6 py-3 border-b border-gray-300">Situação</th>
          <th class="px-6 py-3 border-b border-gray-300">Ação</th>
        </tr>
        </thead>
        <tbody>
          <tr
              v-for="(prof, index) in profissionaisFiltrados"
              :key="index"
              class="hover:bg-purple-50 transition text-center"
          >
          <td class="px-6 py-3 border-b border-gray-300">{{ prof.nome }}</td>
          <td class="px-6 py-3 border-b border-gray-300">{{ prof.profissao }}</td>
          <td class="px-6 py-3 border-b border-gray-300">{{ prof.registro }}</td>
          <td class="px-6 py-3 border-b border-gray-300">
             <span
                 :class="badgeClass(prof.situacao.toLowerCase())"
             >
              {{ prof.situacao.charAt(0).toUpperCase() + prof.situacao.slice(1) }}
            </span>
          </td>
          <!--Coluna de ação e a lógica dos botões-->
          <td class="px-6 py-3 border-b border-gray-300">
            <AcoesProfissional :prof="prof" :recarregar="carregarProfissionais" />
          </td>
        </tr>
        </tbody>
      </table>
    </div>
    <BotaoVoltar class="mt-6" />
  </div>
</template>

<script setup>
  import BotaoVoltar from "../components/BotaoVoltar.vue";
  import AcoesProfissional from '../components/AcoesProfissional.vue'
  import { useAuth } from '../composables/auth.js'
  import {ref, onMounted, computed} from 'vue'
  import axios from 'axios'

  const { profissional: profissionalLogado } = useAuth()
  const profissionais = ref([])
  const erro = ref(null)
  const carregando = ref(false)

  // Lista dos profissionais sem o profissional que está logado
  const profissionaisFiltrados = computed(() =>
      profissionais.value.filter(p => p.uuid !== profissionalLogado.value?.uuid)
  )

  console.log("INFOS ", profissionalLogado)
  console.log("EMAIL ", profissionalLogado.value?.user?.email)

  async function carregarProfissionais() {
    carregando.value = true
    erro.value = null
    try {
      const response = await axios.get('http://localhost:8080/ampara/profissional/listar')
      profissionais.value = response.data
    } catch (e) {
      erro.value = 'Erro ao carregar profissionais. Tente novamente mais tarde.'
      console.error(e)
    } finally {
      carregando.value = false
    }
  }

  onMounted(() => {
    carregarProfissionais()
  })

  function badgeClass(situacao) {
    switch (situacao) {
      case 'ativo':
        return 'bg-green-100 text-green-700 px-3 py-1 rounded-full text-sm font-medium'
      case 'inativo':
        return 'bg-red-100 text-red-700 px-3 py-1 rounded-full text-sm font-medium'
      case 'pendente':
        return 'bg-yellow-100 text-yellow-700 px-3 py-1 rounded-full text-sm font-medium'
      default:
        return 'bg-gray-100 text-gray-700 px-3 py-1 rounded-full text-sm font-medium'
    }
  }
</script>

<style scoped>

</style>