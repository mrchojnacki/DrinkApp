    const stars = document.querySelectorAll(".stars i");
    stars.forEach((star, index1) => {
    star.addEventListener("click", () => {
        let formData = new FormData();
        formData.append('rating', index1+1);
        formData.append("drinkId", drinkId);
        fetch("/addRating", {
            method: "POST",
            body: formData
        })
        stars.forEach((star, index2) => {
            index1 >= index2 ? star.classList.add("active") : star.classList.remove("active");
        })
    })
})
