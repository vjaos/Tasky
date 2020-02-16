<template>
    <v-navigation-drawer permanent clipped mini-variant floating app
                         mini-variant-width="95px"
    >
        <v-list flat>
            <v-list-item
                    v-for="item in items"
                    :key="item.key"
                    :to="item.route"
                    class="mb-5"
                    active-class="dash deep-purple--text text--lighten-1"
            >
                <v-list-item-action>
                    <v-icon v-text="item.icon"/>
                </v-list-item-action>
            </v-list-item>
        </v-list>
    </v-navigation-drawer>
</template>

<script>
    export default {
        name: "Navigation",
        template: "<navigation/>",
        data() {
            return {
                drawer: true,
                items: [
                    {title: 'Dashboard', icon: 'mdi-view-dashboard', route: '/'},
                    {title: 'Projects', icon: 'mdi-folder-outline', route: '/projects'},
                    {title: 'Calendar', icon: 'mdi-calendar-outline', route: '/events'},
                    {title: 'Board', icon: 'mdi-format-list-checks', route: '/board'},
                    {title: 'Settings', icon: 'mdi-cogs', route: '/settings'}
                ]

            }
        }
    }
</script>

<style scoped>

    .dash {
        border-left: 7px solid #7E57C2;
    }

</style>