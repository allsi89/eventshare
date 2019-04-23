function showEventCategories() {
    fetch("/fetch/categories-with-events")
        .then((response) => response.json()
            .then((json) => {
                $('#customText').text('Categories');
                $('#content-container').remove();
                $('.custom-container').append(`<div id="content-container"></div>`);

                let table = `<table class="table table-hover w-75 mx-auto">
                <thead>
                <tr class="row mx-auto">
                    <th class="col-md-2 text-center">#</th>
                    <th class="col-md-5 text-center">Name</th>
                    <th class="col-md-5 text-center">Actions</th>
                </tr>
                </thead>
                <tbody>`;

                json.forEach((category, index) => {
                    table += ` <tr class="row mx-auto">
                        <th class="col-md-2 text-center">${index + 1}</th>
                        <th class="col-md-5 text-center">${category.name}</th>
                        <th class="col-md-5 text-center">
                            <a href="/explore/categories/${category.id}" 
                            class="btn btn-small green">View Events</a>
                        </th>
                    </tr>`;
                });

                table += `</tbody>
            </table>`;

                $('#content-container')
                    .append(table);
            }))
        .catch(() => {
            $('.container #content-container').append(`<h2>There are no pictures in the event gallery!</h2>`)
        })
}

function showEventCountries() {
    fetch("/fetch/countries-with-events")
        .then((response) => response.json()
            .then((json) => {
                $('#customText').text('Countries');
                $('#content-container').remove();
                $('.custom-container').append(`<div id="content-container"></div>`);

                let table = `<table class="table table-hover w-75 mx-auto">
                <thead>
                <tr class="row mx-auto">
                    <th class="col-md-2 text-center">#</th>
                    <th class="col-md-5 text-center">Name</th>
                    <th class="col-md-5 text-center">Actions</th>
                </tr>
                </thead>
                <tbody>`;

                console.log(json);

                json.forEach((country, index) => {
                    table += ` <tr class="row mx-auto">
                        <th class="col-md-2 text-center">${index + 1}</th>
                        <th class="col-md-5 text-center">${country.niceName}</th>
                        <th class="col-md-5 text-center">
                            <a href="/explore/countries/${country.id}" 
                            class="btn btn-small green">View Events</a>
                        </th>
                    </tr>`;
                });

                table += `</tbody>
            </table>`;

                $('#content-container')
                    .append(table);
            }))
        .catch(() => {
            $('.container #content-container').append(`<h2>There are no pictures in the event gallery!</h2>`)
        })
}



function showEventOrganisations() {
    fetch("/fetch/organisations-with-events")
        .then((response) => response.json()
            .then((json) => {
                $('#customText').text('Organisations');
                $('#content-container').remove();
                $('.custom-container').append(`<div id="content-container"></div>`);

                let table = `<table class="table table-hover w-75 mx-auto">
                <thead>
                <tr class="row mx-auto">
                    <th class="col-md-1 text-center">#</th>
                    <th class="col-md-3 text-center">Picture</th>
                    <th class="col-md-4 text-center">Name</th>
                    <th class="col-md-4 text-center">Actions</th>
                </tr>
                </thead>
                <tbody>`;

                console.log(json);

                json.forEach((organisation, index) => {
                    table += ` <tr class="row mx-auto">
                        <th class="col-md-1 text-center">${index + 1}</th>
                        <th class="col-md-3 text-center">
                        <img src="${organisation.imageUrl}" class="thumbnail" width="30" height="30"
                        alt="Organisation Picture"></th>
                        <th class="col-md-4 text-center"> ${organisation.name}</th>
                        <th class="col-md-4 text-center">
                        <a href="/explore/organisations/${organisation.id}" 
                            class="btn btn-small green">View Events</a>
                        </th>
                    </tr>`;
                });

                table += `</tbody>
            </table>`;

                $('#content-container')
                    .append(table);
            }))
        .catch(() => {
            $('.container #content-container').append(`<h2>There are no pictures in the event gallery!</h2>`)
        })

}

function slideshow() {
    $('.carousel').carousel({
            dist: 0,
            padding: 0,
            fullWidth: true,
            indicators: true,
            duration: 100
        }
    );
    autoplay();

    function autoplay() {
        $('.carousel').carousel('next');
        setTimeout(autoplay, 4500);
    }
}



function showGallery(id) {
    fetch("/fetch/created-events/all-pictures/" + id)
        .then((response) => response.json()
            .then((json) => {
                $('#content-container').remove();
                $('.custom-container').append(`<div id="content-container"></div>`);

                console.log(json);
                json.forEach((image, index) => {
                    $('#content-container')
                        .append(`<div class="small-container mt-2">
<img class="materialboxed" src="${image.url}" width="200px" height="200px"></div>`);
                });
                $('.materialboxed').materialbox();
            }))
        .catch(() => {
            $('.container #content-container').append(`<h2>There are no pictures in the event gallery!</h2>`)
        })

}

function showGalleryCreatedEvent(id) {
    fetch("/fetch/created-events/all-pictures/" + id)
        .then((response) => response.json()
            .then((json) => {
                $('#content-container').remove();
                $('.custom-container').append(`<div id="content-container"></div>`);

                console.log(json);
                json.forEach((image, index) => {
                    $('#content-container')
                        .append(`<div class="small-container mt-2">
<img class="materialboxed" src="${image.url}" width="200px" height="200px">
<form action="/events/remove-picture" method="get">
<input type="hidden" name="pictureId" value="${image.id}"> 
<input type="hidden" name="eventId" value="${id}"> 
<button class="btn btn-secondary mt-2" type="submit">Remove</button></form></div>`);

                });
                $('.materialboxed').materialbox();
            }))
        .catch(() => {
            $('.container #content-container').append(`<h2>There are no pictures in the event gallery!</h2>`)
        })

}

