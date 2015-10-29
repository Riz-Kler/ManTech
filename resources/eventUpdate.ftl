<html>
    <head>
        <link rel="stylesheet" href="/style.css"/>
    </head>
    <body>
        <h2>Event details</h2>

        <form action="/event/${event.name}" method="put">
            <div>
                <label for="name">Name&nbsp;</label>
                <input type="text" id="name" name="name" value="${event.name}" />
            </div>
            <div>
                <label for="description">Description&nbsp;</label>
                <input type="text" id="description" name="description"  value="${event.description}" />
            </div>
            <div>
                <label for="date">Date&nbsp;</label>
                <input type="text" id="date" name="date"  value="${event.date}" />
            </div>
            <div>
                <label for="time">Time&nbsp;</label>
                <input type="text" id="time" name="time"  value="${event.time}" />
            </div>
            <div>
                <label for="pictures">Pictures&nbsp;</label>
                <input type="text" id="pictures" name="pictures"  value="${event.picURL}" />
            </div>
            <div>
                <label for="creator">Creator&nbsp;</label>
                <input type="text" id="creator" name="creator"  value="${event.eventCreator}" />
            </div>
            <div>
                <label for="artist">Artist&nbsp;</label>
                <input type="text" id="artist" name="artist"  value="${event.eventArtist}" />
            </div>
            <div>
                <label for="category">Category&nbsp;</label>
                <input type="text" id="category" name="category"  value="${event.eventCategory}" />
            </div>
            <div class="button">
                <button type="submit">Save</button>
            </div>
        </form>

        <a href="/event" class="button">Cancel</a>
    </body>
</html>