package com.portfolio.commons

sealed class CompressorException(message: String) : Exception(message)

class InvalidFileTypeException : CompressorException("Invalid file type")
