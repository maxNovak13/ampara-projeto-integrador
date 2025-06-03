<template>
  <div class="max-w-5xl mx-auto py-12 px-6">

    <div class="grid grid-cols-1 md:grid-cols-2 gap-8">
      <!-- Gerenciar Profissionais (somente para admin) -->
      <div
          v-if="profissional?.adm"
          @click="router.push('/gerenciar-profissional')"
          class="cursor-pointer bg-purple-100 hover:bg-purple-200 p-6 rounded-xl shadow text-center transition shadow-lg shadow-purple-300/50"
      >
        <img src="../assets/profissionais-icon.png" alt="Gerenciar profissionais" class="mx-auto h-20 mb-4" />
        <h2 class="text-xl font-semibold text-black">Gerenciar profissionais</h2>
      </div>

      <!-- Gerenciar Locais (somente para admin) -->
      <div
          v-if="profissional?.adm"
          @click="router.push('/gerenciar-local')"
          class="cursor-pointer bg-purple-100 hover:bg-purple-200 p-6 rounded-xl shadow text-center transition shadow-lg shadow-purple-300/50"
      >
        <img src="../assets/local-icon.png" alt="Gerenciar locais" class="mx-auto h-20 mb-4" />
        <h2 class="text-xl font-semibold text-black">Gerenciar locais</h2>
      </div>

      <!-- Cadastrar Mulher -->
      <div
          @click="router.push('/cadastrar-mulher')"
          class="cursor-pointer bg-purple-100 hover:bg-purple-200 p-6 rounded-xl text-center transition shadow-lg shadow-purple-300/50"
      >
        <img src="../assets/feminino-icon.png" alt="Cadastrar mulher" class="mx-auto h-20 mb-4" />
        <h2 class="text-xl font-semibold text-black">Cadastrar mulher</h2>
      </div>

      <!-- Buscar Mulher -->
      <div
          @click="router.push('/buscar-mulher')"
          class="cursor-pointer bg-purple-100 hover:bg-purple-200 p-6 rounded-xl text-center transition shadow-lg shadow-purple-300/50"
      >
        <img src="../assets/lupa-icon.png" alt="Buscar mulher" class="mx-auto h-20 mb-4" />
        <h2 class="text-xl font-semibold text-black">Buscar mulher</h2>
      </div>
    </div>
  </div>
</template>

<script setup>
  import { useRouter } from 'vue-router'
  import { ref, onMounted } from 'vue'

  const router = useRouter()
  const profissional = ref(null)

  // Carrega o profissional do localStorage ao montar
  onMounted(() => {
    const data = localStorage.getItem('profissional')
    profissional.value = data ? JSON.parse(data) : null
  })
</script>


<style scoped>

</style>