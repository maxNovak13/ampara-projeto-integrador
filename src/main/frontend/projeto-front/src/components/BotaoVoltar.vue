<template>
  <button
      type="button"
      @click="router.back()"
      class=" bg-gray-200 text-gray-800 py-2 px-6 mt-3 rounded hover:bg-gray-300 transition flex items-center gap-2"
  >
     Voltar
  </button>
</template>

<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()
</script>
