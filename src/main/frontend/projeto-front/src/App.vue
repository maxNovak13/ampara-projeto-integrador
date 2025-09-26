<template>
  <div>
    <Navbar></Navbar>
    <router-view />
  </div>
</template>

<script setup>
import Navbar from "./components/Navbar.vue";
import { onMounted } from 'vue';
import { useUserStore } from './stores/user';

const userStore = useUserStore();

onMounted(() => {
  userStore.initialize();
});

</script>

<style>

</style>