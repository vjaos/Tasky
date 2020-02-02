<template>
    <v-app>
        <router-view></router-view>
    </v-app>
</template>

<script>
    export default {
        computed: {
            currentUser() {
                return this.$store.state.auth.user;
            }
        }
    }
</script>

<style scoped>

</style>
