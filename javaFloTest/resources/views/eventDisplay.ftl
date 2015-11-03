<html>
<head>
    <link rel="stylesheet" href="/style.css"/>
</head>
<body>
    <h2>Event we know about</h2>
    <#list event as event>
        <ul>
            <li>
                <a href="/event/${event.name}">${event.name}</a> &nbsp; &nbsp;
                <a href="/event/${event.name}/edit" class=""button>Edit</a> &nbsp; &nbsp;
                <a href="/event/${event.name}/delete" class=""button>Delete</a> &nbsp; &nbsp;
            </li>
        </ul>
        <#else>
            No event to list
    </#list>

    <a href="/event/new" class="button">Add a new event</a>
</body>
</html>