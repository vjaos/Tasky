<template>
    <v-card raised>
        <v-card-title>
            Create an account
        </v-card-title>
        <v-card-text>
            <v-form>
                <v-text-field
                        v-model="user.username"
                        label="Username"
                />
                <v-text-field
                        v-model="user.email"
                        label="E-mail"
                />
                <v-text-field
                        v-model="user.password"
                        label="Password"
                />
            </v-form>
        </v-card-text>
        <v-card-actions>
            <v-btn rounded color="primary" @click="handleSignUp">SIGN UP
            </v-btn>
        </v-card-actions>
    </v-card>
</template>

<script>
    import User from "../models/user";

    export default {
        name: "SignUpComp",
        data() {
            return {
                user: new User('', '', ''),

            }
        },
        methods: {
            handleSignUp() {

            }
        }
    }
</script>

<style scoped>

</style>