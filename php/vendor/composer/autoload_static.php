<?php

// autoload_static.php @generated by Composer

namespace Composer\Autoload;

class ComposerStaticInitd41a0b8cc4ed5e00841d648be58f2093
{
    public static $files = array (
        '0e6d7bf4a5811bfa5cf40c5ccd6fae6a' => __DIR__ . '/..' . '/symfony/polyfill-mbstring/bootstrap.php',
        '25072dd6e2470089de65ae7bf11d3109' => __DIR__ . '/..' . '/symfony/polyfill-php72/bootstrap.php',
        '667aeda72477189d0494fecd327c3641' => __DIR__ . '/..' . '/symfony/var-dumper/Resources/functions/dump.php',
    );

    public static $prefixLengthsPsr4 = array (
        'S' => 
        array (
            'Symfony\\Polyfill\\Php72\\' => 23,
            'Symfony\\Polyfill\\Mbstring\\' => 26,
            'Symfony\\Component\\VarDumper\\' => 28,
        ),
        'P' => 
        array (
            'Phug\\' => 5,
        ),
    );

    public static $prefixDirsPsr4 = array (
        'Symfony\\Polyfill\\Php72\\' => 
        array (
            0 => __DIR__ . '/..' . '/symfony/polyfill-php72',
        ),
        'Symfony\\Polyfill\\Mbstring\\' => 
        array (
            0 => __DIR__ . '/..' . '/symfony/polyfill-mbstring',
        ),
        'Symfony\\Component\\VarDumper\\' => 
        array (
            0 => __DIR__ . '/..' . '/symfony/var-dumper',
        ),
        'Phug\\' => 
        array (
            0 => __DIR__ . '/..' . '/phug/phug/src/Phug/Ast',
            1 => __DIR__ . '/..' . '/phug/phug/src/Phug/Compiler',
            2 => __DIR__ . '/..' . '/phug/phug/src/Phug/DependencyInjection',
            3 => __DIR__ . '/..' . '/phug/phug/src/Phug/Event',
            4 => __DIR__ . '/..' . '/phug/phug/src/Phug/Formatter',
            5 => __DIR__ . '/..' . '/phug/phug/src/Phug/Lexer',
            6 => __DIR__ . '/..' . '/phug/phug/src/Phug/Parser',
            7 => __DIR__ . '/..' . '/phug/phug/src/Phug/Reader',
            8 => __DIR__ . '/..' . '/phug/phug/src/Phug/Renderer',
            9 => __DIR__ . '/..' . '/phug/phug/src/Phug/Util',
        ),
    );

    public static $fallbackDirsPsr0 = array (
        0 => __DIR__ . '/..' . '/phug/phug/src/Phug/Phug',
    );

    public static function getInitializer(ClassLoader $loader)
    {
        return \Closure::bind(function () use ($loader) {
            $loader->prefixLengthsPsr4 = ComposerStaticInitd41a0b8cc4ed5e00841d648be58f2093::$prefixLengthsPsr4;
            $loader->prefixDirsPsr4 = ComposerStaticInitd41a0b8cc4ed5e00841d648be58f2093::$prefixDirsPsr4;
            $loader->fallbackDirsPsr0 = ComposerStaticInitd41a0b8cc4ed5e00841d648be58f2093::$fallbackDirsPsr0;

        }, null, ClassLoader::class);
    }
}
