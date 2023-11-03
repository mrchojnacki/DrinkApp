window.addEventListener('DOMContentLoaded', event => {

    $(document).ready(function () {
        $("#addAlcohol").click(function () {
            const newField = `
                    <div class="alcoholField">
                        <input  name="newAlcoholName" id="newAlcoholName" placeholder="Type in alcohol type"/>
                        <input name="newAlcoholVolume" id="newAlcoholVolume" placeholder="Volume"/>
                        <button class="btn btn-danger btn-sm remove" type="button">Delete this entry</button>
                    </div>
                `;
            $("#alcoholFields").append(newField);
        });
        $("#addIngredient").click(function () {
            const newField = `
                    <div class="ingredientField">
                        <input name="newFillName" id="newFillName" placeholder="Type in ingredient"/>
                        <input name="newFillAmount" id="newFillAmount" placeholder="Amount of ingredient"/>
                        <button class="btn btn-danger btn-sm remove" type="button">Delete this entry</button>
                    </div>
                `;
            $("#ingredientFields").append(newField);
        })
        $("#alcoholFields").on("click", ".remove", function () {
            $(this).parent().remove();
        });
        $("#ingredientFields").on("click", ".remove", function () {
            $(this).parent().remove();
        });
    });

    $(document).ready(function () {
        $('#existingAlcoholIds').select2();
        $('#filterInputAI').on('keyup', function () {
            const filterText = $(this).val().toLowerCase();
            $('#existingAlcoholIds option').each(function () {
                if ($(this).text().toLowerCase().includes(filterText)) {
                    $(this).show();
                } else {
                    $(this).hide();
                }
            });
        });
    });
    $(document).ready(function () {
        $('#existingFillIds').select2();
        $('#filterInputFI').on('keyup', function () {
            const filterText = $(this).val().toLowerCase();
            $('#existingFillIds option').each(function () {
                if ($(this).text().toLowerCase().includes(filterText)) {
                    $(this).show();
                } else {
                    $(this).hide();
                }
            });
        });
    });
});
