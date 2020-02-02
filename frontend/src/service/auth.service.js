import {AXIOS} from "../http-common";


class AuthService {
    login(user) {
        return AXIOS.post(
            'auth/signin', {
                username: user.username,
                password: user.password
            })
            .then(response => {
                if (response.data.token) {
                    localStorage.setItem('user', JSON.stringify(response.data))
                }
                return response.data
            });
    }

    logout() {
        localStorage.removeItem('user')
    }

    register(user) {
        return AXIOS.post('auth/signup', {
            username: user.username,
            password: user.password

        })
    }
}

export default new AuthService()