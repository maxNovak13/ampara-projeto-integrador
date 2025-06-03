<template>
  <button
      @click="router.push('/inicio')"
      class="bg-gray-200 text-gray-800 py-2 px-4 mt-5 rounded hover:bg-gray-300 transition flex items-center gap-2"
  >
    <span>&larr;</span> Voltar
  </button>
</template>

<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()
</script>