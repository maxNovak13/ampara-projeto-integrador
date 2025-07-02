<template>
  <div class="max-w-6xl mx-auto p-4">
    <h2 class="text-2xl font-semibold text-gray-800 mb-4">Histórico de Registros</h2>

    <div v-if="registros.length" class="max-w-4xl mx-auto">
      <table class="min-w-full w-full text-sm text-center border border-gray-200">
        <thead>
        <tr class="bg-purple-100 text-center text-purple-900">
          <th class="px-6 py-3 border-b border-gray-300">Código</th>
          <th class="px-6 py-3 border-b border-gray-300">Data do Registro</th>
          <th class="px-6 py-3 border-b border-gray-300">Local de Atendimento</th>
          <th class="px-6 py-3 border-b border-gray-300">Profissional Responsável</th>
          <th class="px-6 py-3 border-b border-gray-300">Data do Ocorrido</th>
        </tr>
        </thead>
        <tbody class="divide-y divide-gray-200 bg-white">
          <tr
              v-for="registro in registros"
              :key="registro.codigo"
              class="hover:bg-gray-100 cursor-pointer transition duration-150"
              @click="irParaVisualizacao(registro.uuid)"
          >
          <td class="px-6 py-3 border-b border-gray-300">{{ formatarCodigo(registro.creatAt, registro.codigo) }}</td>
          <td class="px-6 py-3 border-b border-gray-300">{{ registro.creatAt }}</td>
          <td class="px-6 py-3 border-b border-gray-300">{{ registro.localAtendimento }}</td>
          <td class="px-6 py-3 border-b border-gray-300">{{ registro.nomeProfissional }}</td>
          <td class="px-6 py-3 border-b border-gray-300">{{ registro.dataOcorrido }}</td>
        </tr>
        </tbody>
      </table>
    </div>
    <div v-else class="text-gray-500 mt-6">Nenhum registro encontrado.</div>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'

const router = useRouter()

function irParaVisualizacao(uuid) {
  router.push({ name: 'VisualizarRegistro', params: { uuid } })
}

const props = defineProps({
  uuidMulher: {
    type: String,
    required: true
  }
})

const registros = ref([])

//buscar registros
watch(
    () => props.uuidMulher,
    async (uuidMulher) => {
      if (!uuidMulher) return
      try {
        const token = localStorage.getItem('token');
        const { data } = await axios.get(`http://localhost:8080/ampara/registro/buscar/${uuidMulher}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        registros.value = data
      } catch (error) {
        console.error('Erro ao buscar registros:', error)
      }
    },
    { immediate: true }
)

//formata código do registro para deixar organizado
function formatarCodigo(createdAt, codigo) {
  const partes = createdAt?.split('/') || []; // ["19", "06", "2025"]
  const ano = partes[2] || '----'; // Pega o ano
  const cod = codigo?.toString().padStart(4, '0');
  return `${ano}-${cod}`;
}
</script>

<style scoped>

</style>