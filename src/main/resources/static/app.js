var appUsers = Vue.createApp({
    data() {
        return {
            users: "",
        };
    },
    methods: {
        showUsers() {
            trackers.showTrackerApp = false;
            fetch("/api/users", {
                method: "GET",
                cache: "no-store",
            })
                .then((response) => {
                    response.json().then((data) => {
                        this.users = data;
                        trackers.trackers = "";
                    });
                })
                .catch((err) => {
                    console.log("there is an error");
                    trackers.trackers = "";
                });
        },
        viewUser(id) {
            trackers.showTracker(id);
        },
        deleteUser(id) {
            fetch("api/users/" + id, {
                method: "DELETE",
                cache: "no-store",
            })
                .then((response) => {
                    if (response.status !== 404) {
                        alert("The item is deleted");
                        this.showUsers();
                    }
                })
                .catch((err) => {
                    console.log("there is an error");
                });
        },
    },
}).mount("#users");

Vue.createApp({
    data() {
        return {
            name: "",
            email: "",
        };
    },
    methods: {
        addUser() {
            if (this.name === "") {
                alert("Name cannot be black");
                return;
            }
            if (this.email === "") {
                alert("Email cannot be black");
                return;
            }
            fetch("/api/users", {
                method: "POST",
                cache: "no-store",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({ name: this.name, email: this.email }),
            })
                .then((response) => {
                    alert(response.status);
                    appUsers.showUsers();
                })
                .catch((err) => {
                    alert("There is an error, the user could not be saved.");
                });
        },
    },
}).mount("#addUser");