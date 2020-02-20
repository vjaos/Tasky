<template>
    <v-app light>
        <navigation v-if="!hide"/>
        <nav-bar v-if="!hide"></nav-bar>
        <v-content app>
            <v-container fluid>
                <router-view></router-view>
            </v-container>
        </v-content>
    </v-app>
</template>

<script>
    import Navigation from "./components/NavigationDrawer";
    import NavBar from "./components/NavBar";

    export default {
        components: {NavBar, Navigation},
        computed: {
            currentUser() {
                return this.$store.state.auth.user;
            },
            hide() {
                return this.$route.path === '/login' || this.$route.path === '/register';
            }
        }
    }
</script>

<style>

    #app {
        margin: 0;
        padding: 0;
        font-family: 'RobotoDraft', 'Roboto', sans-serif;
        font-size: 14px;
        background-color: #fff;
        color: black;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
    }


</style>
