function showEventCategories() {
    fetch("/explore/categories")
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

                console.log(json);

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
    fetch("/explore/countries")
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
    fetch("/explore/organisations")
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
                            <a href="/explore/categories/${organisation.id}" 
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


// function post() {
//     const http =(function () {
//         const send = (url, method, body) => fetch(url, {method, body: JSON.stringify(body)})
//             .then(response => response.json());
//         const post = (url, body) =>send(url, 'POST', body);
//         const get = (url) => send (url, 'GET', null);
//         return{
//             send, post, get
//         };
//
//     }());
//     $('#form').on('submit', (ev) => {
//         const id = $('#btn-id').val();
//         // const id = $(this).attr('data-id');
//         http.post(url, id)
//             .then(() => f(id));
//         ev.preventDefault();
//         return false;
//     })
// }


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

function post() {
    const http = (function () {
        const send = (url, method, body) => fetch(url, {method, body: JSON.stringify(body)})
            .then(response => response.json());
        const post = (url, body) => send(url, 'POST', body);
        const get = (url) => send(url, 'GET', null);
        return {
            send, post, get
        };

    }());
    $('#form').on('submit', (ev) => {
        const id = $('#btn-id').val();
        // const id = $(this).attr('data-id');
        http.post(url, id)
            .then(() => window.location.replace('/home'));
        ev.preventDefault();
        return false;
    })
}

// function f(id) {
//     http.get(url, id).send(url, {method: 'GET', id})
//
// }


function showGallery(id) {
    fetch("/events/all-pictures/" + id)
        .then((response) => response.json()
            .then((json) => {
                $('#content-container').remove();
                $('.custom-container').append(`<div id="content-container"></div>`);

                console.log(json);
                json.forEach((image, index) => {
                    $('#content-container')
                        .append(`<img src="${image.url}" width="200px" height="200px">`);
                    console.log(image.url);
                });
            }))
        .catch(() => {
            $('.container #content-container').append(`<h2>There are no pictures in the event gallery!</h2>`)
        })

}

function showAttendingEvents() {
    fetch("/events/my-events/attending")
        .then((response) => response.json())
        .then((json) => {
            $('#customText').text('Attending events');
            $('#content-container').remove();
            $('.custom-container').append(`<div id="content-container" class="mt-4 text-center"></div>`);

            json.forEach((event, index) => {
                $('#content-container').append(
                    `<h2>Event: ${event.name}</h2>
 <h3>Starts: ${event.startsOnDate.toString()} at ${event.startsOnTime.toString()} </h2>
 <hr class="border"/>
 <a href="/events/my-events/attending/${event.id}" class="btn-flat btn-info rounded">View</a>`);
            });
        })
        .catch(() => {
            $('#customText').text('Attending events');
        });

}


function showCreatedEvents() {
    fetch("/events/my-events/created")
        .then((response) => response.json())
        .then((json) => {
            $('#customText').text('Created events');
            $('#content-container').remove();
            $('.custom-container').append(`<div id="content-container" class="mt-4 text-center"></div>`);

            json.forEach((event, index) => {
                $('#content-container').append(
                    `<h2> Event: ${event.name}</h2>
<h3>Starts: ${event.startsOnDate.toString()} at ${event.startsOnTime.toString()} </h3>
<hr class="border"/>
<a href="/events/my-events/created/${event.id}" class="btn-flat btn-primary">View</a>
<a href="/events/my-events/created/edit/${event.id}" class="btn-flat btn-info">Edit</a>
<a href="/events/my-events/created/delete/${event.id}" class="btn-flat btn-danger">Delete</a><hr/>`);
            });
        })
        .catch(() => {
            $('#customText').text('Created events');
        });


}

function myFunction() {
    $('input').focus(function () {
        $(this).prev().addClass('stylee');
    }).blur(function () {
        if ($(this).val()) {
            $(this).prev().addClass('stylee');
        } else {
            $(this).prev().removeClass('stylee');
        }
    });

    $('select').focus(function () {
        $(this).prev().addClass('stylee');
    }).blur(function () {
        if ($(this).val()) {
            $(this).prev().addClass('stylee');
        } else {
            $(this).prev().removeClass('stylee');
        }
    });
}