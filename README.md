# background-http

Allows you to use of the Native Http framework.

IMPORTANT NOTE: This plugin is a tool I wrote for testing purposes and used to solve a specific problem. You can do everything this library offers and more with the capacitor official native http plugin. I keep it here for my own reference and as an example for the community.

See: https://capacitorjs.com/docs/apis/http

## Install

```bash
npm install background-http
npx cap sync
```

## API

<docgen-index>

* [`echo(...)`](#echo)
* [`sendLocationUpdate(...)`](#sendlocationupdate)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### echo(...)

```typescript
echo(options: { value: string; }) => Promise<{ value: string; }>
```

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ value: string; }</code> |

**Returns:** <code>Promise&lt;{ value: string; }&gt;</code>

--------------------


### sendLocationUpdate(...)

```typescript
sendLocationUpdate(options: { url: string; token: string; username: string; latitude: number; longitude: number; timestamp: number; }) => Promise<{ success: boolean; message: string; }>
```

Sends a location update to the server using native HTTP implementation
that works reliably in the background

| Param         | Type                                                                                                                   | Description                       |
| ------------- | ---------------------------------------------------------------------------------------------------------------------- | --------------------------------- |
| **`options`** | <code>{ url: string; token: string; username: string; latitude: number; longitude: number; timestamp: number; }</code> | The location data and API details |

**Returns:** <code>Promise&lt;{ success: boolean; message: string; }&gt;</code>

--------------------

</docgen-api>
