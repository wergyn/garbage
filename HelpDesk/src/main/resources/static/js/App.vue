<template>
    <div>
        <nav class="navbar is-primary" role="navigation" aria-label="main navigation">
            <div id="navbarBasicExample" class="navbar-menu">
                <div class="navbar-start">
                    <div class="navbar-item">
                        <router-link to="/help-desk/tickets" style="color: inherit; text-decoration: inherit">
                            <h1>Help Desk</h1>
                        </router-link>
                    </div>
                </div>

                <div class="navbar-end">
                    <div class="navbar-item">
                        <div class="userGreetings">
                            <h1>Hello, {{user.firstName}} {{user.lastName}}!</h1>
                        </div>
                    </div>
                    <div class="navbar-item">
                        <div class="buttons">
                            <b-button
                                    class="is-danger"
                                    tag="a"
                                    href="/help-desk/logout">
                                Logout
                            </b-button>
                        </div>
                    </div>
                </div>
            </div>
        </nav>
        <section class="section">
            <router-view :user="user"></router-view>
        </section>
        <footer>
            <div class="content has-text-centered">
                <p>&copy; Alexander Koputerko, 2019</p>
            </div>
        </footer>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                user: {}
            }
        },
        created() {
            this.$http
                .get('http://localhost:8080/help-desk/api/users/current')
                .then(response => (this.user = response.data));
        }
    }
</script>
