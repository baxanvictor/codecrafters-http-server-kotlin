package mappers

import dto.HttpVersion

fun HttpVersion.formatted(): String = "HTTP/$major.$minor"