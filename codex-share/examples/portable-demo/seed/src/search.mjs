export function searchTitles(items, query) {
  return items.filter(item => item.title.includes(query));
}
