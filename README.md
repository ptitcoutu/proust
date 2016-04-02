# proust
Proust (the name is inspired by the french writer known for his "madeleine") is a Business Event Tracker. This framework uses "event" which brings ability to synchronise business moments easily according to callback attached to a cawin (check and wait if necessary).

# Main features
* Event resources : this is the most simple resource on proust. The event is the earth of proust
  1. **id**          : numeric id of the event (its just a way to identify uniquely an event on the server)
  2. **topic**       : a category of the event (in order to have a first organisation of event and a schema which define the payload structure)
  3. **status**      : awaited, occured, invalidated
  4. **description** : partial payload defined in order to "recognise" the business event while event is awaited
  5. **payload**     : payload are only available when the event occured
* Cawin resources : a resource used to call a service in response to the occurence of an event
  1. **id**       : numeric id of the cawin
  2. **event**    : id of the related event
  4. **active**   : if true cawin intercept events by calling callback otherwise the cawin do nothing
  6. **callback** : url of the action to call if event occured or when event will occure

There's two way to push an event : 
 1. the event is pushed (using HTTP PUT) with a description but no payload. In this case Proust looks for an event which matches this description. If an event is found, this event is provided. If no event is found, a new event is created with the awaited status and the related description. This new event is provided.
 2. the event is pushed (using HTTP PUT) with a payload. In this case, Proust looks for the first event in the same topic which matches its description. If there's no matching event, a new event is created with the occured status. If there's a matching event an error is raised : event already occured. 
 
A cawin could be used in order to wait for an event to occur. For exemple if an event is pushed but the response is an awaited event then a cawin could be added in order to wakeup a service which needs the event and its potentially its payload.


  





