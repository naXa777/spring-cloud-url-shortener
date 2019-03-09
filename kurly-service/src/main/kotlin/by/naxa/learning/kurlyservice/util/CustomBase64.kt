/*******************************************************************************
 * Copyright 2015 Tobias Brandt - SoftConEx GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package by.naxa.learning.kurlyservice.util

import net.jcip.annotations.NotThreadSafe
import java.util.*

/**
 * This CustomBase64 class provides the functionality to encode/decode a byte array to/from a Base64 String.<br></br>
 * This CustomBase64 class does NOT follow the RFC 3548 or RFC 4648 specification of Base64 as the used charset can be specified
 * and no padding characters are used.<br></br>
 * So only the characters provided by the user by calling the [.setBase64Charset] will appear in the encoded String and no other characters
 * like equal signs '=' (sometimes used for padding in Base64) will appear. The provided charset should contain *distinct* characters.
 * If user has not provided custom charset, a default charset is used. The default charset is suitable for encoding URL components,
 * since `+` and `/` characters are replaced by `_` and `-`.
 *
 * @author Tobias Brandt
 * @author Pavel Homal
 */
@NotThreadSafe
object CustomBase64 {

    private const val DEFAULT_CHARSET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_-"
    private const val LENGTH_LIMIT = 26

    var base = DEFAULT_CHARSET.length   // 64
        private set
    var base64Charset: String = DEFAULT_CHARSET
        private set
    // int to char mapping
    // 0->0, 1->1, ..., 9->9, 10->a, 11->b, ..., 35->z, 36->A, ..., 61->Z, 62->_, 63->-
    private var base64Chars: CharArray = base64Charset.toCharArray()
    // reverse mapping
    private var charPositionMap: HashMap<Char, Int> = HashMap()

    init {
        setCharset(DEFAULT_CHARSET)
    }

    fun encode(base10number: Long): String {
        if (base10number < 0)
            throw IllegalArgumentException("number can't be negative")

        var base10 = base10number
        val digits = LinkedList<Int>()
        while (base10 > 0) {
            val remainder = (base10 % base).toInt()
            digits.addFirst(remainder)
            base10 /= base
        }

        val sb = StringBuffer()
        for (digit in digits)
            sb.append(base64Chars[digit])

        return sb.toString()
    }

    // max value: OTIyMzM3MjAzNjg1NDc3NTgwNw
    fun decode(base64String: String): Long {
        if (base64String.length > LENGTH_LIMIT)
            throw IllegalArgumentException("A number represented by the encoded string is too large.")

        val chars = base64String.toCharArray()
        var id = 0L
        var exp = chars.size - 1
        for (i in 0..chars.size) {
            val base10 = charPositionMap[chars[i]]
                    ?: throw IllegalArgumentException("The string contains an unknown character: ${chars[i]} (position: $i).")
            id += (base10 * Math.pow(base.toDouble(), exp.toDouble())).toLong()
            --exp
        }
        return id
    }

    /**
     * The base64Charset must be a String containing distinct characters.<br></br>
     * If it does contain duplicate characters a [IllegalArgumentException] is thrown.
     * @param base64Charset
     */
    fun setCharset(base64Charset: String) {
        val base64Chars = base64Charset.toCharArray()
        val base = base64Charset.length
        val charPositionMap: HashMap<Char, Int> = HashMap()
        for (i in base64Chars.indices)
            charPositionMap[base64Chars[i]] = i
        if (charPositionMap.size != base)
            throw IllegalArgumentException("The charset must contain $base unique characters.")

        this.base64Charset = base64Charset
        this.base64Chars = base64Chars
        this.base = base
        this.charPositionMap = charPositionMap
    }

}
