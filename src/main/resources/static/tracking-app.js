var trackers = Vue.createApp({
    data() {
        return {
            trackers: "",
            calories: "",
            walkHours: "",
            drinking:"",
            userId: "",
            showTrackerApp: false
        };
    },
    methods: {
        showTracker(userId) {
            this.showTrackerApp = true;
            this.userId = userId;
            fetch("/api/trackers/"+userId, {
                method: "GET",
                cache: "no-store",
            })
                .then((response) => {
                    response.json().then((data) => {
                        this.trackers = data;
                    }).catch((err)=>{
                        trackers.trackers = "";
                    });
                })
                .catch((err) => {
                    console.log("there is an error");
                    trackers.trackers = "";
                });
        },
        deleteTracker(id) {
            fetch("api/trackers/" + id, {
                method: "DELETE",
                cache: "no-store",
            })
                .then((response) => {
                    if (response.status !== 404) {
                        alert("The item is deleted");
                        this.showTracker(this.userId);
                    }
                })
                .catch((err) => {
                    console.log("there is an error");
                });
        },
        addTracker() {
            if (this.calories === "") {
                alert("Calories cannot be black");
                return;
            }
            if (this.walkHours === "") {
                alert("Walking cannot be black");
                return;
            }
            if (this.drinking === "") {
                alert("Drinking cannot be black");
                return;
            }
            fetch("/api/trackers", {
                method: "POST",
                cache: "no-store",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({ userId: this.userId, calories: this.calories, drinking: this.drinking, walkHours: this.walkHours }),
            })
                .then((response) => {
                    alert(response.status);
                    this.showTracker(this.userId);
                })
                .catch((err) => {
                    alert("There is an error, the tracker could not be saved.");
                });
        }
    },
}).mount("#trackers");
