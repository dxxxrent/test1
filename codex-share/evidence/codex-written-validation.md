# Search query fix validation

## Requested behavior

- Trim leading and trailing query whitespace and match without case sensitivity.
- Return every item for an empty (including whitespace-only) query.
- Throw a clear `TypeError` when the query is not a string.
- Leave the input array and its entries unchanged.

## Changes

- Updated `src/search.mjs` to validate and normalize the query, return a shallow array copy for an empty query, and compare normalized text without changing item objects.
- Kept the existing acceptance assertions in `test/search.test.mjs`.

## Validation

- `npm test` — failed before test execution because Node's test runner could not spawn its test process (`spawn EPERM`, Node v24.21.0). The same environment error is present in the supplied `baseline-test.log`.
- Direct Node behavior assertions — passed for case-insensitive matching, leading/trailing whitespace, whitespace-only query, non-string query errors, unchanged entries, and object identity.

No external services or substitute data were used. The full `node:test` suite remains unverified in this environment due to the process-spawn restriction.
