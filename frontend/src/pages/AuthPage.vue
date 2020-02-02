<template>
    <v-container>
        <div>
            <transition name="auth" mode="out-in">
                <component v-bind:is="view"></component>
            </transition>
        </div>
        <div>
            <v-btn rounded @click="changeView" >{{ btnText }}</v-btn>
        </div>
    </v-container>
</template>

<script>
    import Login from "../components/Login";
    import SignUp from "../components/SignUp";

    export default {
        name: "AuthPage",
        data() {
            return {
                show: false,
                view: 'login',
                btnText: 'SIGN IN'
            }
        },
        components: {
            'login': Login,
            'signup': SignUp
        },
        methods: {
            changeView() {
                this.show = !this.show
                this.view = this.show ? 'signup' : 'login'
                this.btnText = this.show ? 'SIGN UP' : 'SIGN IN'
            }
        }
    }
</script>

<style scoped>

</style>