# background-http

allows you to issue background http requests

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
