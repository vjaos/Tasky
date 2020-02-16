<template>
    <v-container>
        <div class="form">
            <div class="form-panel">
                <div class="form-header">
                    <h1>Login to Tasky</h1>
                </div>
                <div class="form-content">
                    <v-form>
                        <div class="form-group">
                            <label for="username">Username</label>
                            <input v-model="user.username" type="text" id="username"
                                   required="required">
                        </div>
                        <div class="form-group">
                            <label for="password">Password</label>
                            <input v-model="user.password" type="password" id="password"
                                   required="required"/>
                        </div>
                        <div class="form-group">
                            <label class="form-remember">
                                <input type="checkbox"/>Remember Me
                            </label><a class="form-recovery" href="#">Forgot Password?</a>
                        </div>
                        <div class="form-group">
                            <button @click="handleLogin" type="submit">Log In</button>
                        </div>
                    </v-form>
                </div>
            </div>
        </div>
    </v-container>

</template>

<script>
    import User from "../models/user";


    export default {
        name: "LoginView",
        data() {
            return {
                user: new User('', '', ''),
                show: true,
                message: ''
            }
        },
        created() {
            if (this.loggedIn) {
                this.$router.push('/')
            }
        }
        ,
        computed: {
            loggedIn() {
                return this.$store.state.auth.status.loggedIn();
            }
        }
        ,
        methods: {
            handleLogin() {
                if (this.user.username && this.user.password) {
                    this.$store.dispatch('auth/login', this.user).then(
                        () => {
                            this.$router.push('/')
                        },
                        error => {
                            this.message =
                                (error.response && error.response.data) ||
                                error.message || error.toString()
                        }
                    )
                }
            }
        }
    }
</script>

<style scoped>

    .form {
        z-index: 15;
        position: relative;
        background: #ffffff;
        width: 600px;
        border-radius: 15px;
        box-shadow: 0 0 30px rgba(0, 0, 0, 0.1);
        box-sizing: border-box;
        margin: 100px auto auto;
        overflow: hidden;
    }


    .form-group {
        display: -webkit-box;
        display: flex;
        flex-wrap: wrap;
        -webkit-box-pack: justify;
        justify-content: space-between;
        margin: 0 0 20px;
    }

    .form-group label {
        display: block;
        margin: 0 0 10px;
        color: rgba(0, 0, 0, 0.6);
        font-size: 12px;
        font-weight: 500;
        line-height: 1;
        text-transform: uppercase;
        letter-spacing: 0.2em;
    }

    .form-group input {
        outline: none;
        display: block;
        background: rgba(0, 0, 0, 0.1);
        width: 100%;
        border: 0;
        border-radius: 4px;
        box-sizing: border-box;
        padding: 12px 20px;
        color: rgba(0, 0, 0, 0.6);
        font-family: inherit;
        font-size: inherit;
        font-weight: 500;
        line-height: inherit;
        -webkit-transition: 0.3s ease;
        transition: 0.3s ease;
    }

    .form-group:last-child {
        margin: 0;
    }

    .form-header {
        margin: 0 0 40px;
    }

    .form-header h1 {
        padding: 4px 0;
        color: #4285f4;
        font-size: 24px;
        font-weight: 700;
        text-transform: uppercase;
    }


    .form-group button {
        outline: none;
        background: #4285f4;
        width: 100%;
        border: 0;
        border-radius: 4px;
        padding: 12px 20px;
        color: #ffffff;
        font-family: inherit;
        font-size: inherit;
        font-weight: 500;
        line-height: inherit;
        text-transform: uppercase;
        cursor: pointer;
    }

    .form-group .form-remember {
        font-size: 12px;
        font-weight: 400;
        letter-spacing: 0;
        text-transform: none;
    }

    .form-group .form-remember input[type="checkbox"] {
        display: inline-block;
        width: auto;
        margin: 0 10px 0 0;
    }

    .form-group .form-recovery {
        color: #4285f4;
        font-size: 12px;
        text-decoration: none;
    }

    .form-panel {
        padding: 60px 60px 60px 60px;
        box-sizing: border-box;
    }

    .form-panel:before {
        content: "";
        display: block;
        opacity: 0;
        visibility: hidden;
        -webkit-transition: 0.3s ease;
        transition: 0.3s ease;
    }

</style>