<html>
    <head>
        <link rel="stylesheet" href="/css/style.css"/>
    </head>
    <body>
        <h2>Event Creation</h2>

        <form action="/event" method="post">
            <div>
                <label for="name">Name&nbsp;</label>
                <input type="text" id="name" name="name" />
            </div>
            <div>
                <label for="description">Description&nbsp;</label>
                <input type="text" id="description" name="description" />
            </div>
            <div>
                <label for="date">Date&nbsp;</label>
                <input type="text" id="date" name="date" />
            </div>
            <div>
                <label for="time">Time&nbsp;</label>
                <input type="text" id="time" name="time" />
            </div>
            <div>
                <label for="picurl">Picture&nbsp;</label>
                <input type="text" id="picurl" name="picurl" />
            </div>
            <div>
                <label for="creator">Creator&nbsp;</label>
                <input type="text" id="creator" name="creator" />
            </div>   
            <div>
                <label for="artist">Artist&nbsp;</label>
                <input type="text" id="date" name="artist" />
            </div>     
            <div>
                <label for="category">Category&nbsp;</label>
                <input type="text" id="category" name="category" />
            </div>
            
            <div class="button">
                <button type="submit">Create</button>
            </div>
        </form>

        <a href="/event" class=""button>Cancel</a>
    </body>
</html>
