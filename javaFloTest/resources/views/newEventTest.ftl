
<html>
	<head>
		<meta charset="utf-8">
		<link href="/css/CreateEvent.css" rel="stylesheet" />
		<title>Create Event</title>	
	</head>
	
	<body>
		<form action="/event" id="CreateEvent" method="post">

		<fieldset id="EventInput">
		
		<h1>Create Event</h1>
		
		
			<label for="eventname">Name&nbsp;</label>
			<input id="eventname" type="text" name="eventname" placeholder="EventName">

			<label for="location">Location&nbsp;</label>
			<input id="location" type="text" name="location" placeholder="Location">

			<label for="organizor">Organizor&nbsp;</label>
			<input id="organizor" type="text" name="organizor"  placeholder="Organizor"/>

			<label for="artist">Artist&nbsp;</label>
			<input id="artist" type="text" name="artist"  placeholder="Artist"/>

			<label for="category">Category&nbsp;</label>
			<input id="category" type="text" name="category"  placeholder="Category"/>

			<label for="description">Description&nbsp;</label>
			<textarea id="description" name="description" cols="" rows=""></textarea>

			<label for="date">Date&nbsp;</label>
			<input id="date" type="date" name="eventdate"/>

			<label for="time">Time&nbsp;</label>
			<input id="time" type="time" name="eventtime"/>
			
		</fieldset>



		<div>
			<label>Upload Pic:</label><input id="file" name="file" multiple type="file"/>
		</div>

		<fieldset id="actions"> 
            <div class="button">
                <button type="submit" id="submit">Create</button>
            </div>
		</fieldset> 
		
		</form>
	</body>
</html>
