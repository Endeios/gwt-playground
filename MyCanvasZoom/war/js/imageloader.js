/*
The MIT License

Copyright (c) 2011 Matthew Wilcoxson (www.akademy.co.uk)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/
/*
ImageLoader
By Matthew Wilcoxson

Description:    Download image files when you need them.
Example:        http://www.akademy.co.uk/software/canvaszoom/canvaszoom.php
Version:        1.0.4
*/
function ImageLoader( settings )
{
    var NULL = null,UNDEFINED=undefined,TRUE=true,FALSE=false;
	
    var thatImageLoader = this;
    var checkComplete, complete;
    
	this.images = [];

	this.begun = FALSE;

	this.onAllLoaded = NULL;
	if( settings.onAllLoaded !== UNDEFINED )
	{	this.onAllLoaded = settings.onAllLoaded;	}

	this.onImageLoaded = NULL;
	if( settings.onImageLoaded !== UNDEFINED )
	{	this.onImageLoaded = settings.onImageLoaded;	}

	for( i = 0; i < settings.images.length; i++ ) {
		var name = '';
		var id = 0;
		
		if( settings.images[i] ) {
			if( settings.images[i].name != UNDEFINED )
					name = settings.images[i].name;
			if( settings.images[i].id != UNDEFINED )
					id = settings.images[i].id;
					
			this.images[i] = new LoadImage( name, id, i, settings.images[i].file );
        }
	}
	
    this.imageCount = this.images.length;
    this.begun = TRUE;
	
	this.getImageByPosition = function( position ) {
		for( i = 0; i < this.imageCount; i++ )
			if( position == this.images[i].position )
				if( this.images[i].loaded )
					return this.images[i].image;
				else
                    return NULL; // Not loaded
	
        return UNDEFINED; // Not found
	};	
	
	this.getImageById = function( id ) {
		for( i = 0; i < this.imageCount; i++ )
			if( id == this.images[i].id )
				if( this.images[i].loaded )
					return this.images[i].image;
				else
					return NULL; // Not loaded
	
        return UNDEFINED; // Not found
	};
	
	this.getImageByName = function( name ) {
		for( i = 0; i < this.imageCount; i++ )
			if( name == this.images[i].name )
				if( this.images[i].loaded )
					return this.images[i].image;
				else
					return NULL; // Not loaded
	
                return UNDEFINED; // Not found
	};
	
	this.loadedIds = function ( idArray ) {
		if( this.begun ) {
			for( j = 0; j < idArray.length; j++ )
				for( i = 0; i < this.imageCount; i++ )
					if( idArray[j] == this.images[i].id )
						if( this.images[i].loaded == false )
							return FALSE;
								
				return TRUE;
		}
		
		return FALSE;
	};
	
	this.loadedNames = function ( nameArray ) {
		if( this.begun ) {
			for( j = 0; j < nameArray.length; j++ )
				for( i = 0; i < this.imageCount; i++ )
					if( nameArray[j] == this.images[i].name )
						if( this.images[i].loaded == false )
                            return FALSE;
								
            return TRUE;
		}
		
        return FALSE;
	};
	
	this.loadedAll = function() {
		if( this.begun ) {
			for( i = 0; i < this.imageCount; i++ )
				if( this.images[i].loaded == FALSE )
						return FALSE;
			
            return TRUE;
		}
		
        return FALSE;
	};
	
	var setLoaded = function( position ) {
		for( i = 0; i < thatImageLoader.imageCount; i++ )
			if( position == thatImageLoader.images[i].position ) {
				thatImageLoader.images[i].Done();
				if( thatImageLoader.onImageLoaded != null )
                                        thatImageLoader.onImageLoaded( thatImageLoader.images[i].name, thatImageLoader.images[i].image, thatImageLoader.images[i].file );
			}
	
		checkComplete();
	};
	
	checkComplete = function() {
		for( i = 0; i < thatImageLoader.imageCount; i++ )
			if( !thatImageLoader.images[i].loaded )
				return;
		
		complete();
	};
	
	complete = function() {
		if( thatImageLoader.onAllLoaded != NULL )
			thatImageLoader.onAllLoaded();
	};
	
	function LoadImage( name, id, position, file ) {
		var thatLoadImage = this;
		
		this.name = name;
		this.id = id;
		this.position = position;
		this.file = file;
		this.loaded = false;
		
		this.image = new Image();
		this.image.onload = function() { setLoaded( thatLoadImage.position ); };

		this.image.src = this.file; // Set last.
		
		this.Done = function () {
			thatLoadImage.loaded = true;
			thatLoadImage.image.onload = thatLoadImage.image.onabort = thatLoadImage.image.onerror = null;
		};
	};
}
