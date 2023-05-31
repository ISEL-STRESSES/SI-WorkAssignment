package logic.mappers;

/*
MIT License

Copyright (c) 2022, Nuno Datia, ISEL

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

/**
 * Interface that defines the methods that a DataMapper must implement to be able
 * to perform CRUD operations on the database.
 *
 * @param <T>  Entity type
 * @param <TK> Entity key type
 */
public interface DataMapper<T, TK> {
    /**
     * Creates a new entity in the database.
     *
     * @param entity Entity to be created
     * @return The key of the created entity
     */
    TK create(T entity);

    /**
     * Reads an entity from the database.
     *
     * @param id Key of the entity to be read
     * @return The entity with the given key
     */
    T read(TK id);

    /**
     * Updates an entity in the database.
     *
     * @param entity Entity to be updated
     * @return The key of the updated entity
     */
    TK update(T entity);

    /**
     * Deletes an entity from the database.
     *
     * @param id Key of the entity to be deleted
     */
    void delete(TK id);
}
