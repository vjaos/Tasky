<template>
    <v-card raised
    >
        <v-card-title>
            Sign in to Tasky
        </v-card-title>
        <v-card-text>
            <v-form @submit.prevent="handleLogin">
                <v-text-field
                        v-model="user.username"
                        label="Login"
                        type="text"
                        prepend-icon="mdi-account"
                >
                </v-text-field>
                <v-text-field
                        v-model="user.password"
                        prepend-icon="mdi-key"
                        label="Password"
                        type="text"
                >
                </v-text-field>
                <v-btn rounded :disabled="loading" :loading="loading" color="primary" @click="handleLogin">
                    SIGN IN
                </v-btn>
                <div v-if="message" role="alert">{{message}}</div>
            </v-form>
        </v-card-text>
    </v-card>

</template>

<script>
    import User from "../models/user";

    export default {
        name: "LoginComp",
        data() {
            return {
                user: new User('', '', ''),
            }
        },
        computed: {
            loggedIn() {
                return this.$store.state.auth.status.loggedIn
            }
        },
        created() {
            if (this.loggedIn) {
                this.$router.push('/')
            }
        },
        methods: {
            handleLogin() {
                this.loading = true
                if (this.user.username && this.user.password) {
                    this.$store.dispatch('auth/login', this.user).then(
                        () => {
                            this.$router.push('/')
                        },
                        error => {
                            this.loading = false
                            this.message = (error.response && error.response.data) ||
                                error.message || error.toString()
                        }
                    )
                }
            }
        }
    }
</script>

<style scoped>

</style>